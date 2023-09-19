package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.FrostDamageAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class Frost implements Effect {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "frost");

    private static final double BASE_COST = 10;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.atLeastOneOf(SpellcraftTargetTypes.ITEM, SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.frost");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 30, 255, 255);

    private static final float BASE_DAMAGE = 5;

    @Override
    public void cast(final Spell spell, final Caster caster, final Targets targets, final Level level) {
        final var attributes = spell.getShape().getAttributes();

        final var durationAttribute = attributes.get(SpellcraftAttributeTypes.DURATION);
        final var duration = durationAttribute.getDurationInTicks();

        final var damageAttribute = attributes.get(SpellcraftAttributeTypes.FROST_DAMAGE);
        final var damage = damageAttribute.getDamage();

        final var mobEffectInstancesAttribute = attributes.get(SpellcraftAttributeTypes.MOB_EFFECT_INSTANCES);
        final var mobEffectInstances = mobEffectInstancesAttribute.getMobEffectInstances();

        var success = false;

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            for (final var mobEffectInstance : mobEffectInstances) {
                entity.setTicksFrozen(duration);

                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(mobEffectInstance));
                }
            }

            final var damageSources = SpellcraftDamageSources.of(entity);

            entity.hurt(
                    damageSources.spellcraft_frost(spell, caster.asEntity(), entity),
                    damage
            );

            spell.playSuccessSound(caster, level);
            success = true;
        }

        for (final var blockTarget : targets.getAllOfType(SpellcraftTargetTypes.BLOCK)) {
            final var block = blockTarget.getValue();

            final var state = level.getBlockState(block.position());
            if (state.getBlock() instanceof SnowLayerBlock snow) {
                if (snow.canSurvive(state, level, block.position().above())) {
                    level.setBlockAndUpdate(block.position().above(), snow.defaultBlockState());

                    if (caster instanceof Player player) {
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, block.position().above());
                    }

                    success = true;
                }
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
        attributes.create(SpellcraftAttributeTypes.FROST_DAMAGE, new FrostDamageAttribute(BASE_DAMAGE));
    }
}

