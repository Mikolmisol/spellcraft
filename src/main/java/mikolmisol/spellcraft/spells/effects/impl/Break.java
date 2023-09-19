package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.MiningLevelAttribute;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class Break implements Effect {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "break");

    private static final double BASE_COST = 5;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.BLOCK);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.break");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(256, 200, 200, 1);

    private static final int BASE_MINING_LEVEL = MiningLevelAttribute.MiningLevel.STONE;

    private boolean tryBreakBlock(@NotNull BlockTarget block, int miningLevel, @NotNull Spell spell, Caster caster, Level level) {
        final var state = level.getBlockState(block.position());

        final var requiredMiningLevel = MiningLevelManager.getRequiredMiningLevel(state);

        if (miningLevel >= requiredMiningLevel) {
            level.destroyBlock(block.position(), true);

            for (final var modifier : spell.getShape().getModifiers()) {
                if (modifier instanceof Diffuse) {
                    for (final var neighbour : Diffuse.findNeighbours(block.position(), block.direction(), level)) {
                        tryBreakBlock(neighbour, miningLevel, spell, caster, level);
                    }
                }
            }


            return true;
        }

        return false;
    }

    @Override
    public void cast(final Spell spell, final Caster caster, final @NotNull Targets targets, final Level level) {
        var success = false;

        final var attributes = spell.getShape().getAttributes();
        final var miningLevelAttribute = attributes.get(SpellcraftAttributeTypes.MINING_LEVEL);

        if (miningLevelAttribute == null) {
            return;
        }

        final var miningLevel = miningLevelAttribute.getMiningLevel();

        for (final var blockTarget : targets.getAllOfType(SpellcraftTargetTypes.BLOCK)) {
            if (tryBreakBlock(blockTarget.getValue(), miningLevel, spell, caster, level)) {
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
    }
}
