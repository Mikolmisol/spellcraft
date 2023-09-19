package mikolmisol.spellcraft.spells.shapes;

import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Shape extends Comparable<Shape> {
    static Shape fromIdentifier(@NotNull ResourceLocation identifier) {
        return Objects.requireNonNull(SpellcraftRegistries.SHAPE.get(identifier));
    }

    /**
     * Return true if the spell casting went successfully and Energy
     * should be taken from caster, false otherwise.
     */
    void cast(final Spell spell, final Caster caster, final Level level);

    double getCost();

    /**
     * @return The name of the Shape to be used in user interfaces.
     */
    @NotNull Component getName();

    /**
     * @return A List of all possible targets that this Shape is capable of providing.
     */
    @NotNull ProvidedTargets getProvidedTargets();

    @NotNull ResourceLocation getIdentifier();

    void defineBaseAttributes(@NotNull Attributes attributes);

    @Override
    default int compareTo(@NotNull Shape shape) {
        if (this == shape) {
            return 0;
        }

        return getIdentifier().compareTo(shape.getIdentifier());
    }
}
