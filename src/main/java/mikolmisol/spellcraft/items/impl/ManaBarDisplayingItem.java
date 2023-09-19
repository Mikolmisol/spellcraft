package mikolmisol.spellcraft.items.impl;

import mikolmisol.spellcraft.items.ManaContainingItem;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ManaBarDisplayingItem extends Item implements ManaContainingItem {
    public ManaBarDisplayingItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isBarVisible(ItemStack item) {
        final var mana = getManaStorage(item);

        if (mana == null) {
            return false;
        }

        return mana.getAmount() < mana.getCapacity();
    }

    @Override
    public int getBarWidth(ItemStack item) {
        final var mana = getManaStorage(item);

        if (mana == null) {
            return 0;
        }

        return (int) Math.floor(mana.getAmount() * 13.0 / mana.getCapacity());
    }

    @Override
    public int getBarColor(ItemStack item) {
        return 1176048 + (int) Math.sin(Util.getMillis() / 100.0);
    }
}
