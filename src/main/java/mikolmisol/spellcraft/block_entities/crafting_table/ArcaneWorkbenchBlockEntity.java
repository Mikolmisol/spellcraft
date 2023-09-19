package mikolmisol.spellcraft.block_entities.crafting_table;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.container.SimpleImplementedCraftingContainer;
import mikolmisol.spellcraft.menus.arcane_workbench.ArcaneWorkbenchMenu;
import mikolmisol.spellcraft.util.ContainerUtil;
import mikolmisol.spellcraft.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ArcaneWorkbenchBlockEntity extends BaseContainerBlockEntity implements SimpleImplementedCraftingContainer, RecipeHolder {
    public static final int RESULT_SLOT = 0;

    public static final int CRAFTING_SLOT_1 = 1;

    public static final int CRAFTING_SLOT_2 = 2;

    public static final int CRAFTING_SLOT_3 = 3;

    public static final int CRAFTING_SLOT_4 = 4;

    public static final int CRAFTING_SLOT_5 = 5;

    public static final int CRAFTING_SLOT_6 = 6;

    public static final int CRAFTING_SLOT_7 = 7;

    public static final int CRAFTING_SLOT_8 = 8;

    public static final int CRAFTING_SLOT_9 = 9;

    public static final int SLOT_COUNT = 10;

    private static final Component NAME;

    public final NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    private @Nullable Recipe<?> recipe;

    public ArcaneWorkbenchBlockEntity(BlockPos position, BlockState block) {
        super(SpellcraftBlockEntityTypes.ARCANE_WORKBENCH, position, block);
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return items;
    }

    @Override
    protected Component getDefaultName() {
        return NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory inventory) {
        return new ArcaneWorkbenchMenu(
                syncId,
                inventory,
                this,
                ContainerLevelAccess.create(level, getBlockPos())
        );
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        final var tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerUtil.saveAllItems(tag, items);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerUtil.loadAllItems(tag, items);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @Nullable Recipe<?> getRecipeUsed() {
        return recipe;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        this.recipe = recipe;
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {

    }

    static {
        NAME = TextUtil.translate(
                String.format("gui.%s.arcane_workbench", Spellcraft.MOD_ID),
                "Arcane Workbench"
        );
    }
}
