package mikolmisol.spellcraft.menus.arcane_core;

import mikolmisol.spellcraft.menus.SpellcraftMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ArcaneCoreMenu extends AbstractContainerMenu {
    public ArcaneCoreMenu(int syncId, @NotNull Inventory inventory) {
        super(SpellcraftMenuTypes.ARCANE_CORE, syncId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
