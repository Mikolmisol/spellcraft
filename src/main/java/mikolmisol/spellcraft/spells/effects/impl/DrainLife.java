package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.ArcaneDamageAttribute;
import mikolmisol.spellcraft.spells.attributes.impl.HealingAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class DrainLife implements Effect {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "drain_life");

    private static final double BASE_COST = 5;

    private static final float BASE_DAMAGE = 4;

    private static final float BASE_HEALING = 2;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.just(SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.drain_life");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(256, 200, 1, 200);


    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {

        final var attributes = spell.getShape().getAttributes();

        final var damageAttribute = attributes.get(SpellcraftAttributeTypes.ARCANE_DAMAGE);

        if (damageAttribute == null) {
            return;
        }

        final var healingAttribute = attributes.get(SpellcraftAttributeTypes.HEALING);

        if (healingAttribute == null) {
            return;
        }

        final var damage = damageAttribute.getDamage();
        final var healing = healingAttribute.getHealing();

        for (final var target : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var targetEntity = target.getValue();

            final var damageSources = SpellcraftDamageSources.of(targetEntity);

            if (targetEntity.hurt(damageSources.spellcraft_arcane(spell, caster.asEntity(), targetEntity), damage)) {
                if (caster instanceof LivingEntity livingCaster) {
                    livingCaster.heal(healing);
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
        attributes.create(SpellcraftAttributeTypes.ARCANE_DAMAGE, new ArcaneDamageAttribute(BASE_DAMAGE));
        attributes.create(SpellcraftAttributeTypes.HEALING, new HealingAttribute(BASE_HEALING));
    }
}
