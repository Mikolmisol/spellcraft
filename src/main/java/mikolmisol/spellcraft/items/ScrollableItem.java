package mikolmisol.spellcraft.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ScrollableItem extends SpellContainingItem {
    void onShiftScrollWhileHolding(Player player, ItemStack currentItem, int scrollDelta);
}
