package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.DurationAttribute;
import mikolmisol.spellcraft.spells.attributes.impl.FireDamageAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import mikolmisol.spellcraft.util.TimeUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class Fire implements Effect {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "fire");

    private static final double BASE_COST = 10;

    private static final float BASE_DAMAGE = 4;

    private static final int BASE_DURATION = TimeUtil.secondsToTicks(5);

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.atLeastOneOf(SpellcraftTargetTypes.ITEM, SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.fire");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 102, 0, 255);

    private final RecipeManager.CachedCheck<Container, SmeltingRecipe> recipeProvider = RecipeManager.createCheck(RecipeType.SMELTING);

    @Override
    public void cast(final Spell spell, final Caster caster, final Targets targets, final Level level) {
        final var attributes = spell.getShape().getAttributes();

        final var durationAttribute = attributes.get(SpellcraftAttributeTypes.DURATION);

        if (durationAttribute == null) {
            return;
        }

        final var duration = durationAttribute.getDurationInTicks();

        final var damageAttribute = attributes.get(SpellcraftAttributeTypes.FIRE_DAMAGE);

        if (damageAttribute == null) {
            return;
        }

        final var damage = damageAttribute.getDamage();

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();
            final var damageSources = SpellcraftDamageSources.of(entity);

            if (level.isClientSide) {

            } else {
                entity.setSecondsOnFire((int) TimeUtil.ticksToSeconds(duration));
                entity.hurt(
                        damageSources.spellcraft_fire(
                                spell,
                                caster.asEntity(),
                                entity
                        ),
                        damage
                );

                spell.playSuccessSound(caster, level);
            }
        }

        for (final var blockTarget : targets.getAllOfType(SpellcraftTargetTypes.BLOCK)) {
            final var block = blockTarget.getValue();

            if (level.isClientSide) {

            } else {
                final var hitPos = block.position();
                final var hitState = level.getBlockState(hitPos);

                if (CampfireBlock.canLight(hitState) || CandleBlock.canLight(hitState) || CandleCakeBlock.canLight(hitState)) {
                    level.setBlock(hitPos, hitState.setValue(BlockStateProperties.LIT, true), 11);

                    if (caster instanceof Player player) {
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, hitPos);
                    }

                } else if (block.direction() != null) {
                    if (BaseFireBlock.canBePlacedAt(level, hitPos, block.direction())) {
                        final var placementState = BaseFireBlock.getState(level, hitPos);
                        level.setBlock(hitPos, placementState, 11);

                        if (caster instanceof Player player) {
                            level.gameEvent(player, GameEvent.BLOCK_PLACE, hitPos);
                        }

                    } else {
                        final var targetedBlockPos = switch (block.direction()) {
                            case DOWN -> hitPos.below(1);
                            case UP -> hitPos.above(1);
                            case NORTH -> hitPos.north(1);
                            case SOUTH -> hitPos.south(1);
                            case WEST -> hitPos.west(1);
                            case EAST -> hitPos.east(1);
                        };

                        if (BaseFireBlock.canBePlacedAt(level, targetedBlockPos, block.direction())) {
                            final var placementState = BaseFireBlock.getState(level, targetedBlockPos);
                            level.setBlock(targetedBlockPos, placementState, 11);

                            if (caster instanceof Player player) {
                                level.gameEvent(player, GameEvent.BLOCK_PLACE, targetedBlockPos);
                            }
                        }
                    }
                }
            }
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
        attributes.create(SpellcraftAttributeTypes.FIRE_DAMAGE, new FireDamageAttribute(BASE_DAMAGE));
        attributes.create(SpellcraftAttributeTypes.DURATION, new DurationAttribute(BASE_DURATION));
    }
}
