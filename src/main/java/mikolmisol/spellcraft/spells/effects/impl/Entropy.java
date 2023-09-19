package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.ArcaneDamageAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class Entropy implements Effect {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "entropy");

    private static final double BASE_COST = 15;

    private static final float BASE_DAMAGE = 3;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.atLeastOneOf(SpellcraftTargetTypes.ITEM, SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("spell.effect.spellcraft.entropy");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(100, 10, 100, 255);

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {
        final var attributes = spell.getShape().getAttributes();

        final var damageAttribute = attributes.get(SpellcraftAttributeTypes.ARCANE_DAMAGE);

        if (damageAttribute == null) {
            return;
        }

        final var damage = damageAttribute.getDamage();

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            if (entity instanceof LivingEntity livingEntity) {
                final var armors = livingEntity.getArmorSlots();
                for (final var armor : armors) {
                    armor.hurtAndBreak((int) Math.floor(damage), livingEntity, owner -> {
                        owner.level.broadcastEntityEvent(owner, EntityEvent.CHEST_BREAK);
                    });
                }
            }

            final var damageSources = SpellcraftDamageSources.of(entity);

            entity.hurt(
                    damageSources.spellcraft_arcane(
                            spell,
                            caster.asEntity(),
                            entity
                    ),
                    damage
            );

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
    }
}
