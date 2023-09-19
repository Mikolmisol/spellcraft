package mikolmisol.spellcraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class SpellcraftBlockWithBlockEntity extends BaseEntityBlock {
    public SpellcraftBlockWithBlockEntity(Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.is(blockState2.getBlock())) {
            return;
        }

        final var blockEntity = level.getBlockEntity(blockPos);

        if (!level.isClientSide && blockEntity instanceof Container container) {
            Containers.dropContents(level, blockPos, container);
        }

        level.updateNeighbourForOutputSignal(blockPos, this);

        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState block, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return getClientTicker(level, block, type);
        }

        return getServerTicker(level, block, type);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState block, BlockEntityType<T> type) {
        return null;
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getClientTicker(Level level, BlockState block, BlockEntityType<T> type) {
        return null;
    }
}
