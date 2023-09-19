package mikolmisol.spellcraft.items;

import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ManaContainingItem {
    default boolean canOwnerExtractMana(@NotNull Player owner, @NotNull ItemStack item) {
        return false;
    }

    default boolean canOwnerInsertMana(@NotNull Player owner, @NotNull ItemStack item) {
        return true;
    }

    default @Nullable ManaStorage getManaStorage(ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return null;
        }

        final var mana = ManaStorage.fromTag(tag);
        mana.setOnManaChangedListener(amount -> mana.toTag(item.getOrCreateTag()));

        return mana;
    }
}
