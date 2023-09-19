package mikolmisol.spellcraft.spells.effects.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.accessors.ThrallAccessor;
import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.MobEffectInstancesAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import mikolmisol.spellcraft.util.TimeUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

// Enthrall and subjugate the target entity, turning it into a mindless slave that will defend you with their life.
public final class BloodControl implements Effect {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "blood_control");

    private static final double BASE_COST = 50;

    private static final int BASE_DURATION = TimeUtil.secondsToTicks(60);

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.blood_control");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(256, 0, 256, 256);

    private static final int BASE_AMPLIFIER = 1;

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {
        final var attributes = spell.getShape().getAttributes();

        final var mobEffectInstancesAttribute = attributes.get(SpellcraftAttributeTypes.MOB_EFFECT_INSTANCES);

        if (mobEffectInstancesAttribute == null) {
            return;
        }

        final var mobEffectInstances = mobEffectInstancesAttribute.getMobEffectInstances();

        var success = false;

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            if (level.isClientSide) {

            } else if (caster instanceof LivingEntity livingCaster) {

                success = true;

                if (entity instanceof LivingEntity livingEntity) {
                    if (entity instanceof ThrallAccessor thrall) {
                        thrall.spellcraft_setMaster(livingCaster);
                    }

                    for (final var mobEffectInstance : mobEffectInstances) {
                        livingEntity.addEffect(new MobEffectInstance(mobEffectInstance));
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
        attributes.create(
                SpellcraftAttributeTypes.MOB_EFFECT_INSTANCES,
                new MobEffectInstancesAttribute(
                        Lists.newArrayList(
                                SpellcraftMobEffects.createInstance(
                                        SpellcraftMobEffects.ARCANE_BARRIER,
                                        BASE_DURATION,
                                        BASE_AMPLIFIER
                                )
                        )
                )
        );
    }
}
