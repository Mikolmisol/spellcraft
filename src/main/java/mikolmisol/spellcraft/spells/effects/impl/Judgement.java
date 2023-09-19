package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.DurationAttribute;
import mikolmisol.spellcraft.spells.attributes.impl.FireDamageAttribute;
import mikolmisol.spellcraft.spells.attributes.impl.HealingAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import mikolmisol.spellcraft.util.TimeUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class Judgement implements Effect {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "judgement");

    private static final double BASE_COST = 10;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.judgement");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 1, 102, 220);

    private static final float BASE_DAMAGE = 4;

    private static final float BASE_HEALING = 3;

    private static final int BASE_DURATION = TimeUtil.secondsToTicks(5);

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {
        var success = false;

        final var attributes = spell.getShape().getAttributes();

        final var damageAttribute = attributes.get(SpellcraftAttributeTypes.FIRE_DAMAGE);
        final var damage = damageAttribute.getDamage();

        final var healingAttribute = attributes.get(SpellcraftAttributeTypes.HEALING);
        final var healing = healingAttribute.getHealing();

        final var durationEffect = attributes.get(SpellcraftAttributeTypes.DURATION);
        final var duration = durationEffect.getDurationInTicks();

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            if (level.isClientSide) {

            } else {
                success = true;

                final var damageSources = SpellcraftDamageSources.of(entity);

                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getMobType() == MobType.UNDEAD) {
                        livingEntity.setSecondsOnFire(duration);
                        livingEntity.hurt(
                                damageSources.spellcraft_fire(
                                        spell,
                                        caster.asEntity(),
                                        entity
                                ),
                                damage * 3f
                        );
                    } else {
                        livingEntity.heal(healing);
                    }
                }

                spell.playSuccessSound(caster, level);
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
        attributes.create(SpellcraftAttributeTypes.FIRE_DAMAGE, new FireDamageAttribute(BASE_DAMAGE));
        attributes.create(SpellcraftAttributeTypes.HEALING, new HealingAttribute(BASE_HEALING));
        attributes.create(SpellcraftAttributeTypes.DURATION, new DurationAttribute(BASE_DURATION));
    }
}
