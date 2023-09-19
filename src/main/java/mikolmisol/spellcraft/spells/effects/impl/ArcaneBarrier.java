package mikolmisol.spellcraft.spells.effects.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.Spellcraft;
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

public final class ArcaneBarrier implements Effect {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "arcane_barrier");

    private static final double BASE_COST = 20;

    private static final int BASE_DURATION = TimeUtil.secondsToTicks(10);

    private static final int BASE_AMPLIFIER = 1;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.arcane_barrier");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 255, 0, 255);

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {

        final var attributes = spell.getShape().getAttributes();

        final var mobEffectInstancesAttribute = attributes.get(SpellcraftAttributeTypes.MOB_EFFECT_INSTANCES);

        if (mobEffectInstancesAttribute == null) {
            return;
        }

        final var mobEffectInstances = mobEffectInstancesAttribute.getMobEffectInstances();

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            if (level.isClientSide) {

            } else {
                if (entity instanceof LivingEntity livingEntity) {
                    for (final var mobEffectInstance : mobEffectInstances) {
                        livingEntity.addEffect(new MobEffectInstance(mobEffectInstance));
                    }
                }
            }

            spell.playSuccessSound(caster, level);
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
