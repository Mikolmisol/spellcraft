package mikolmisol.spellcraft.items;

import mikolmisol.spellcraft.spells.Spell;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpellContainingItem {
    String TAG_SPELL = "SpellcraftSpell";

    default @Nullable Spell getSpell(final @NotNull ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return null;
        }

        final var spellTag = tag.getCompound(TAG_SPELL);
        return Spell.fromTag(spellTag);
    }

    default void putSpell(final @NotNull ItemStack item, final @NotNull Spell spell) {
        final var tag = item.getOrCreateTag();
        tag.put(TAG_SPELL, Spell.toTag(spell));
    }
}
