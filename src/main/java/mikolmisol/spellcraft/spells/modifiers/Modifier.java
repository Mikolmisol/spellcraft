package mikolmisol.spellcraft.spells.modifiers;

import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Modifier extends Comparable<Modifier> {
    static Modifier fromIdentifier(@NotNull ResourceLocation identifier) {
        return Objects.requireNonNull(SpellcraftRegistries.MODIFIER.get(identifier));
    }

    @NotNull Component getName();

    /**
     * Determine the stackability of this Modifier, i.e., how many times this modifier
     * can be applied to a single spell. This is only ever checked during the making
     * of a spell; it is the responsibility of the code making the spell to enforce it!
     *
     * @return The maximum stack size of this modifier that can be applied to a single spell.
     */
    int getMaximumStackSize();

    @NotNull ResourceLocation getIdentifier();

    @Override
    default int compareTo(@NotNull Modifier modifier) {
        if (this == modifier) {
            return 0;
        }

        return getIdentifier().compareTo(modifier.getIdentifier());
    }
}
