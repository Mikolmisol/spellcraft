package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.MiningLevelAttribute;
import mikolmisol.spellcraft.spells.attributes.impl.VeinmineAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.modifiers.impl.Diffuse;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import mikolmisol.spellcraft.spells.targets.impl.BlockTarget;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.NotNull;

public final class Harvest implements Effect {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "harvest");

    private static final double BASE_COST = 15;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.BLOCK);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.harvest");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 255, 255, 10);

    private static final int BASE_MINING_LEVEL = MiningLevelAttribute.MiningLevel.WOOD;

    private boolean tryHarvestBlock(BlockTarget block, @NotNull Spell spell, Caster caster, Level level) {
        final var state = level.getBlockState(block.position());

        final var attributes = spell.getShape().getAttributes();

        final var miningLevel = attributes.get(SpellcraftAttributeTypes.MINING_LEVEL).getMiningLevel();
        final var shouldVeinmine = attributes.get(SpellcraftAttributeTypes.VEINMINE).isVeinmine();

        if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)) {
            final var requiredMiningLevel = MiningLevelManager.getRequiredMiningLevel(state);

            if (miningLevel >= requiredMiningLevel) {
                level.levelEvent(2001, block.position(), Block.getId(state));

                final var blockEntity = level.getBlockEntity(block.position());
                Block.dropResources(state, level, block.position(), blockEntity, caster instanceof Entity entity ? entity : null, ItemStack.EMPTY);

                spell.playSuccessSound(caster, level);

                for (final var modifier : spell.getShape().getModifiers()) {
                    if (modifier instanceof Diffuse) {
                        for (final var neighbour : Diffuse.findNeighbours(block.position(), block.direction(), level)) {
                            tryHarvestBlock(neighbour, spell, caster, level);
                        }
                    }
                }

                level.setBlockAndUpdate(block.position(), crop.getStateForAge(1));

                return true;
            }
        } else if (state.getBlock() instanceof CocoaBlock cocoa && state.getValue(CocoaBlock.AGE) == CocoaBlock.MAX_AGE) {
            final var facing = state.getValue(CocoaBlock.FACING);
            level.setBlockAndUpdate(block.position(), cocoa.defaultBlockState().setValue(CocoaBlock.FACING, facing));
            final var blockEntity = level.getBlockEntity(block.position());
            Block.dropResources(state, level, block.position(), blockEntity, caster instanceof Entity entity ? entity : null, ItemStack.EMPTY);

            spell.playSuccessSound(caster, level);

            for (final var modifier : spell.getShape().getModifiers()) {
                if (modifier instanceof Diffuse) {
                    for (final var neighbour : Diffuse.findNeighbours(block.position(), block.direction(), level)) {
                        tryHarvestBlock(neighbour, spell, caster, level);
                    }
                }
            }

            return true;
        } else if (state.getBlock() instanceof SugarCaneBlock && level.getBlockState(block.position().above()).is(Blocks.SUGAR_CANE)) {
            level.destroyBlock(block.position().above(), true, caster instanceof Entity entity ? entity : null);

            spell.playSuccessSound(caster, level);

            for (final var modifier : spell.getShape().getModifiers()) {
                if (modifier instanceof Diffuse) {
                    for (final var neighbour : Diffuse.findNeighbours(block.position(), block.direction(), level)) {
                        tryHarvestBlock(neighbour, spell, caster, level);
                    }
                }
            }

            return true;
        } else if (state.getBlock() instanceof StemGrownBlock) {
            level.destroyBlock(block.position(), false);
            final var blockEntity = level.getBlockEntity(block.position());
            Block.dropResources(state, level, block.position(), blockEntity, caster instanceof Entity entity ? entity : null, ItemStack.EMPTY);

            spell.playSuccessSound(caster, level);

            for (final var neighbour : Diffuse.findNeighbours(block.position(), block.direction(), level)) {
                tryHarvestBlock(neighbour, spell, caster, level);
            }

            return true;
        }

        return false;
    }

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {
        var success = false;

        for (final var blockTarget : targets.getAllOfType(SpellcraftTargetTypes.BLOCK)) {
            final var block = blockTarget.getValue();

            if (tryHarvestBlock(block, spell, caster, level)) {
                success = true;
            }
        }

        if (!success) {
            spell.playFailureSound(caster, level);
        }
    }

    @Override
    public @NotNull Component getName() {
        return NAME;
    }

    @Override
    public int getDecimalColor() {
        return DECIMAL_COLOR;
    }

    @Override
    public @NotNull RequiredTargets getRequiredTargets() {
        return REQUIRED_TARGETS;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public double getCost() {
        return BASE_COST;
    }

    @Override
    public void defineBaseAttributes(@NotNull Attributes attributes) {
        attributes.create(SpellcraftAttributeTypes.MINING_LEVEL, new MiningLevelAttribute(BASE_MINING_LEVEL));
        attributes.create(SpellcraftAttributeTypes.VEINMINE, new VeinmineAttribute(false));
    }
}
