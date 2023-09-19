package mikolmisol.spellcraft.blocks.barrier;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.barrier.ArcaneBarrierBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneBarrierBlock extends SpellcraftBlockWithBlockEntity {
    public ArcaneBarrierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArcaneBarrierBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, SpellcraftBlockEntityTypes.ARCANE_BARRIER, ArcaneBarrierBlockEntity::serverTick);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getClientTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, SpellcraftBlockEntityTypes.ARCANE_BARRIER, ArcaneBarrierBlockEntity::clientTick);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {

        if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof ArcaneBarrierBlockEntity barrier) {
            ArcaneBarrierBlockEntity.deactivateAllProjectors(level, barrier);
        }

        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }
}
