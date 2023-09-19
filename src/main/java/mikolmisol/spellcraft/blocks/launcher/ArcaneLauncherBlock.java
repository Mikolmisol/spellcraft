package mikolmisol.spellcraft.blocks.launcher;

import mikolmisol.spellcraft.block_entities.launcher.ArcaneLauncherBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

public final class ArcaneLauncherBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING;

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }

    public ArcaneLauncherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArcaneLauncherBlockEntity(blockPos, blockState);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos position, Block block, BlockPos blockPos2, boolean bl) {
        if (level.getBestNeighborSignal(position) < 1) {
            return;
        }

        final var blockEntity = level.getBlockEntity(position);
        if (blockEntity instanceof ArcaneLauncherBlockEntity arcaneCannonBlockEntity) {
            arcaneCannonBlockEntity.fireProjectile(level);
        }

        super.neighborChanged(blockState, level, position, block, blockPos2, bl);
    }
}
