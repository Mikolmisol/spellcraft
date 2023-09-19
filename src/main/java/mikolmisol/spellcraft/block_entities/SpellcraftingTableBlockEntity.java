package mikolmisol.spellcraft.block_entities;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.block_entities.container.SimpleImplementedContainer;
import mikolmisol.spellcraft.menus.spell_crafting_table.SpellCraftingTableMenu;
import mikolmisol.spellcraft.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpellcraftingTableBlockEntity extends BaseContainerBlockEntity implements SimpleImplementedContainer {

    private static final Component NAME;

    public static final int SLOT_COUNT = 2;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    private Component name;

    public SpellcraftingTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.SPELL_CRAFTING_TABLE, blockPos, blockState);
    }

    @Override
    public Component getName() {
        if (name == null) {
            return getDefaultName();
        }
        return name;
    }

    @Override
    public Component getDisplayName() {
        return getName();
    }

    protected Component getDefaultName() {
        return NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory inventory) {
        return new SpellCraftingTableMenu(syncId, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, items);
        if (hasCustomName()) {
            tag.putString("CustomName", Component.Serializer.toJson(name));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        ContainerHelper.loadAllItems(tag, items);
        if (tag.contains("CustomName", 8)) {
            name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return items;
    }

    static {
        NAME = TextUtil.translate(
                String.format("gui.%s.spell_crafting_table", Spellcraft.MOD_ID),
                "Spellcrafting Table"
        );
    }
}
