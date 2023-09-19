package mikolmisol.spellcraft.items;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface SelectableItem {
    void onSelected(@NotNull ItemStack item);
}
