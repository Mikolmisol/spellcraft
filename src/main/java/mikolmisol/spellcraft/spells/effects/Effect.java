package mikolmisol.spellcraft.spells.effects;

import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Effect extends Comparable<Effect> {

    static Effect fromIdentifier(@NotNull ResourceLocation identifier) {
        return Objects.requireNonNull(SpellcraftRegistries.EFFECT.get(identifier));
    }

    /**
     * Return true if the spell casting went successfully and Energy
     * should be taken from caster, false otherwise.
     */
    void cast(final Spell spell, final Caster caster, final Targets targets, final Level level);

    @NotNull Component getName();

    /**
     * Return the color of the spell's visual effects. For example, fiery spells should be orange.
     * The color is returned in a decimal representation. You can use
     * {@link net.minecraft.util.FastColor.ARGB32#color(int, int, int, int)}.
     */
    int getDecimalColor();

    /**
     * @return A List of all Target types without which this effect cannot do anything at all.
     */
    @NotNull RequiredTargets getRequiredTargets();

    @NotNull ResourceLocation getIdentifier();

    double getCost();

    void defineBaseAttributes(@NotNull Attributes attributes);

    @Override
    default int compareTo(@NotNull Effect effect) {
        if (this == effect) {
            return 0;
        }

        return getIdentifier().compareTo(effect.getIdentifier());
    }
}
