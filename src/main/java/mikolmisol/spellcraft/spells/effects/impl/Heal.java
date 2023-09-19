package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.DurationAttribute;
import mikolmisol.spellcraft.spells.attributes.impl.HealingAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class Heal implements Effect {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "heal");

    public static final double BASE_COST = 20;
    public static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.ENTITY);
    public static final int DECIMAL_COLOR = FastColor.ARGB32.color(200, 10, 255, 255);
    private static final float BASE_HEALING = 5;
    private static final int BASE_DURATION = 0;
    private static final Component NAME = Component.translatable("spell.effect.spellcraft.heal");

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {
        var success = false;

        final var attributes = spell.getShape().getAttributes();

        final var healingAttribute = attributes.get(SpellcraftAttributeTypes.HEALING);
        final var healing = healingAttribute.getHealing();

        final var durationAttribute = attributes.get(SpellcraftAttributeTypes.DURATION);
        final var duration = durationAttribute.getDurationInTicks();

        final var regenerationAmplifier = (int) Math.ceil(healing / 5.0f);

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            if (level.isClientSide) {

            } else {
                if (entity instanceof LivingEntity livingEntity) {
                    if (duration > 0) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, regenerationAmplifier));
                    } else {
                        livingEntity.heal(healing);
                    }

                    success = true;
                }
            }


            if (success) {
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
        attributes.create(SpellcraftAttributeTypes.HEALING, new HealingAttribute(BASE_HEALING));
        attributes.create(SpellcraftAttributeTypes.DURATION, new DurationAttribute(BASE_DURATION));
    }
}
