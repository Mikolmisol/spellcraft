package mikolmisol.spellcraft.block_entities.crafting_table;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.storage.WorldlyManaStorage;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.util.SinusoidalCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneCraftingFocusBlockEntity extends BlockEntity implements WorldlyManaStorage {
    public final SinusoidalCounter counter = new SinusoidalCounter(-0.25f, 0.25f, 0.01f);
    private final NonNullList<ItemStack> item = NonNullList.withSize(1, ItemStack.EMPTY);
    private ManaStorage mana = ManaStorage.creative();

    public ArcaneCraftingFocusBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.ARCANE_CRAFTING_FOCUS, blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        mana.toTag(tag);
        ContainerHelper.saveAllItems(tag, item);
    }

    @Override
    public void load(CompoundTag tag) {
        mana = ManaStorage.fromTag(tag);
        ContainerHelper.loadAllItems(tag, item);
    }

    @Override
    public @Nullable ManaStorage getManaStorageForDirection(@NotNull Direction direction) {
        return mana;
    }

    @Override
    public @Nullable ManaStorage getManaStorage() {
        return mana;
    }
}
