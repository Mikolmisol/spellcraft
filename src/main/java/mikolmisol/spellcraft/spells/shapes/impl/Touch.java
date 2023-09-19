package mikolmisol.spellcraft.spells.shapes.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class Touch implements Shape {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "touch");

    private static final double BASE_COST = 5;

    private static final Component NAME = Component.translatable("spell.shape.spellcraft.touch");

    private static final ProvidedTargets PROVIDED_TARGETS = ProvidedTargets.of(SpellcraftTargetTypes.ITEM, SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static double REACH = 3;

    @Override
    public void cast(final Spell spell, final Caster caster, final Level level) {
        if (caster instanceof ServerPlayer player) {
            Spellcraft.sendRequestRaycastPacket(player, REACH, targets -> {
                for (final var effect : spell.getShape().getEffects()) {
                    effect.getEffect().cast(spell, caster, targets, level);
                }
            });
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
