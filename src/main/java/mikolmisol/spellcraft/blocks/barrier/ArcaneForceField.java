package mikolmisol.spellcraft.blocks.barrier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ArcaneForceField extends CrossCollisionBlock {
    private static final double ZAP_MANA_COST = 1;

    public ArcaneForceField(Properties properties) {
        super(1, 1, 16, 16, 16, properties);
        registerDefaultState(
                getStateDefinition()
                        .any()
                        .setValue(NORTH, false)
                        .setValue(SOUTH, false)
                        .setValue(EAST, false)
                        .setValue(WEST, false)
                        .setValue(WATERLOGGED, false)
        );
    }

    public static int getLightLevelForState(BlockState state) {
        return 12;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        final var level = blockPlaceContext.getLevel();
        final var pos = blockPlaceContext.getClickedPos();
        final var fluidState = level.getFluidState(pos);
        return defaultBlockState()
                .setValue(NORTH, level.getBlockState(pos.north()).is(this))
                .setValue(SOUTH, level.getBlockState(pos.south()).is(this))
                .setValue(EAST, level.getBlockState(pos.east()).is(this))
                .setValue(WEST, level.getBlockState(pos.west()).is(this))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos pos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return defaultBlockState()
                .setValue(NORTH, level.getBlockState(pos.north()).is(this))
                .setValue(SOUTH, level.getBlockState(pos.south()).is(this))
                .setValue(EAST, level.getBlockState(pos.east()).is(this))
                .setValue(WEST, level.getBlockState(pos.west()).is(this));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        final var forceFieldPositionVector = Vec3.atCenterOf(pos);
        final var entityPositionVector = entity.position();
        final var distance = forceFieldPositionVector.distanceToSqr(entityPositionVector);

        final var pushVector = forceFieldPositionVector.vectorTo(entityPositionVector).scale(2 / distance * 0.3);

        entity.push(pushVector.x, 0, pushVector.z);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.empty();
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos blockPos, BlockState blockState) {
    }
}
