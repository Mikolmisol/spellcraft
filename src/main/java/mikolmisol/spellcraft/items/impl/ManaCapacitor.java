package mikolmisol.spellcraft.items.impl;

import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/*
    The problem of any spellcaster is that even though Energy permeates the universe
    and is refilled naturally over time, we usually do not use it for long periods
    only to find ourselves desperately low on it when ambushed by mobs.

    Energy Collectors present a solution to this age-old issue: though expensive, they
    accumulate mana in times of surplus and serve as mana sources in times of need.
 */
public final class ManaCapacitor extends ManaBarDisplayingItem {
    private static final double MAXIMUM_MANA = 50;

    public ManaCapacitor(Properties properties) {
        super(properties);
    }


    @Override
    public boolean canOwnerExtractMana(@NotNull Player owner, @NotNull ItemStack item) {
        return true;
    }

    @Override
    public boolean canOwnerInsertMana(@NotNull Player owner, @NotNull ItemStack item) {
        return true;
    }

    @Override
    public @NotNull ManaStorage getManaStorage(ItemStack item) {
        return ManaStorage.creative();
    }
}
