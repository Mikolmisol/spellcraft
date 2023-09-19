package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class Transmute implements Effect {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "transmute");

    private static final double BASE_COST = 10;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.atLeastOneOf(SpellcraftTargetTypes.ITEM, SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("effect.spellcraft.transmute");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 255, 0, 255);

    @Override
    public void cast(final @NotNull Spell spell, final Caster caster, final Targets targets, final Level level) {

        final var entities = targets.getAllOfType(SpellcraftTargetTypes.ENTITY);

        for (final var entity : entities) {

        }

        spell.playSuccessSound(caster, level);
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

    }
}
