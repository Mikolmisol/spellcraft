package mikolmisol.spellcraft.menus.spell_crafting_table;

import mikolmisol.spellcraft.menus.SpellcraftMenuTypes;
import mikolmisol.spellcraft.util.MenuUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class SpellCraftingTableMenu extends AbstractContainerMenu {
    public SpellCraftingTableMenu(int syncId, @NotNull Inventory inventory) {
        super(SpellcraftMenuTypes.SPELL_CRAFTING_TABLE, syncId);

        MenuUtil.addHotbar(this, inventory, 100, 100);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
