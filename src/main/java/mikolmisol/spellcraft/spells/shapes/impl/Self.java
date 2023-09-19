package mikolmisol.spellcraft.spells.shapes.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Target;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public final class Self implements Shape {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "self");

    private static final double BASE_COST = 5;

    private static final Component NAME = Component.translatable("spell.shape.spellcraft.self");

    private static final ProvidedTargets PROVIDED_TARGETS = ProvidedTargets.of(SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    @Override
    public void cast(final @NotNull Spell spell, final Caster caster, final Level level) {

        final var targets = Lists.<Target<?>>newArrayList();

        if (caster instanceof BlockEntity block) {
            targets.add(Target.ofBlock(block.getBlockPos(), null));
        }

        if (caster instanceof LivingEntity entity) {
            targets.add(Target.ofEntity(entity));
        }

        for (final var effect : spell.getShape().getEffects()) {
            effect.getEffect().cast(spell, caster, Targets.of(targets), level);
        }
    }

    @Override
    public double getCost() {
        return BASE_COST;
    }

    @Override
    public @NotNull Component getName() {
        return NAME;
    }

    @Override
    public @NotNull ProvidedTargets getProvidedTargets() {
        return PROVIDED_TARGETS;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void defineBaseAttributes(@NotNull Attributes attributes) {

    }
}
