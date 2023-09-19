package mikolmisol.spellcraft.menus.arcane_workbench;

import mikolmisol.spellcraft.block_entities.crafting_table.ArcaneWorkbenchBlockEntity;
import mikolmisol.spellcraft.block_entities.container.SimpleImplementedCraftingContainer;
import mikolmisol.spellcraft.menus.SpellcraftMenuTypes;
import mikolmisol.spellcraft.util.MenuUtil;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static mikolmisol.spellcraft.block_entities.crafting_table.ArcaneWorkbenchBlockEntity.RESULT_SLOT;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;

    private final Inventory inventory;

    private final CraftingContainer container;

    public ArcaneWorkbenchMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new SimpleImplementedCraftingContainer() {
            @Override
            public void fillStackedContents(StackedContents stackedContents) {

            }

            @Override
            public void setChanged() {

            }

            @Override
            public @NotNull List<ItemStack> getItems() {
                return Collections.emptyList();
            }

        }, ContainerLevelAccess.NULL);
    }

    public ArcaneWorkbenchMenu(int syncId, Inventory inventory, CraftingContainer container, ContainerLevelAccess access) {
        super(SpellcraftMenuTypes.ARCANE_WORKBENCH, syncId);
        this.inventory = inventory;
        this.container = container;
        this.access = access;

        // add result slot
        addSlot(new Slot(container, 0, 124, 35));

        // add craft slots
        MenuUtil.addGrid(this, this.container, 30, 17);

        // add inventory
        MenuUtil.addInventoryAndHotbar(this, inventory, 8, 84);
    }

    private static void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, ArcaneWorkbenchBlockEntity container) {
        if (level.isClientSide) {
            return;
        }

        final var serverPlayer = (ServerPlayer) player;
        var result = ItemStack.EMPTY;

        final var maybeRecipe = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level);

        if (maybeRecipe.isPresent()) {
            final var recipe = maybeRecipe.get();
            if (container.setRecipeUsed(level, serverPlayer, recipe)) {
                result = recipe.assemble(container, level.registryAccess());
            }
        }

        container.setItem(RESULT_SLOT, result);
        menu.setRemoteSlot(RESULT_SLOT, result);

        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), RESULT_SLOT, result));
    }

    @Override
    public void slotsChanged(Container container) {
        access.execute((level, pos) -> {
            if (container instanceof ArcaneWorkbenchBlockEntity arcaneWorkbench) {
                slotChangedCraftingGrid(this, level, inventory.player, arcaneWorkbench);
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        var result = ItemStack.EMPTY;

        final var slot = slots.get(i);
        if (slot != null && slot.hasItem()) {
            var slotItem = slot.getItem();
            result = slotItem.copy();

            if (i == 0) {
                access.execute((level, blockPos) -> {
                    slotItem.getItem().onCraftedBy(slotItem, level, player);
                });

                if (!moveItemStackTo(slotItem, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotItem, result);

            } else if (i >= 10 && i < 46) {

                if (!moveItemStackTo(slotItem, 1, 10, false)) {
                    if (i < 37) {
                        if (!moveItemStackTo(slotItem, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }

                    } else if (!moveItemStackTo(slotItem, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }

            } else if (!moveItemStackTo(slotItem, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);

            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotItem);
            if (i == 0) {
                player.drop(slotItem, false);
            }
        }

        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
