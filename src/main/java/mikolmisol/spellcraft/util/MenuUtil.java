package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class MenuUtil {

    public void addInventory(@NotNull AbstractContainerMenu menu, @NotNull Inventory inventory, int x, int y) {
        for (var j = 0; j < 3; ++j) {
            for (var k = 0; k < 9; ++k) {
                menu.addSlot(new Slot(inventory, k + j * 9 + 9, x + k * 18, y + j * 18));
            }
        }
    }

    public void addHotbar(@NotNull AbstractContainerMenu menu, @NotNull Inventory inventory, int startX, int y) {
        for (var j = 0; j < 9; ++j) {
            menu.addSlot(new Slot(inventory, j, startX + j * 18, y));
        }
    }

    public void addGrid(@NotNull AbstractContainerMenu menu, @NotNull CraftingContainer container, int x, int y) {
        for (var j = 0; j < 3; ++j) {
            for (var k = 0; k < 3; ++k) {
                menu.addSlot(new Slot(container, k + j * 3 + 1, x + k * 18, y + j * 18));
            }
        }
    }

    public void addInventoryAndHotbar(@NotNull AbstractContainerMenu menu, @NotNull Inventory inventory, int x, int y) {
        addInventory(menu, inventory, x, y);
        addHotbar(menu, inventory, x, y + 58);
    }
}
