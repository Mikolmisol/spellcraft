package mikolmisol.spellcraft.blocks.relay;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.relay.ArcaneRelayBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneRelayBlock extends BaseEntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final VoxelShape SHAPE_UP = Shapes.box(0.3, 0, 0.3, 0.7, 0.75, 0.7);

    private static final VoxelShape SHAPE_DOWN = Shapes.box(0.3, 0.25, 0.3, 0.7, 1, 0.7);

    private static final VoxelShape SHAPE_NORTH = Shapes.box(0.3, 0.3, 0.3, 0.7, 0.75, 1);

    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0.3, 0.3, 0, 0.7, 0.75, 0.7);

    private static final VoxelShape SHAPE_EAST = Shapes.box(0, 0.3, 0.3, 0.75, 0.75, 0.7);

    private static final VoxelShape SHAPE_WEST = Shapes.box(0.3, 0.3, 0.3, 1, 0.75, 0.7);

    public ArcaneRelayBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcaneRelayBlockEntity(position, block);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState block, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }

        return createTickerHelper(
                type,
                SpellcraftBlockEntityTypes.MANA_RELAY,
                ArcaneRelayBlockEntity::serverTick
        );
    }

    @Override
    public boolean canSurvive(BlockState block, LevelReader level, BlockPos position) {
        final var direction = block.getValue(FACING);
        final var supportBlockPosition = position.relative(direction.getOpposite());
        return level.getBlockState(supportBlockPosition).isFaceSturdy(level, supportBlockPosition, direction);
    }

    @Override
    public BlockState updateShape(BlockState block, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos position, BlockPos blockPos2) {
        if (block.getValue(WATERLOGGED)) {
            level.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction == block.getValue(FACING).getOpposite() && !canSurvive(block, level, position)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(block, direction, blockState2, level, position, blockPos2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext place) {
        final var level = place.getLevel();
        final var position = place.getClickedPos();
        return defaultBlockState()
                .setValue(WATERLOGGED, level.getFluidState(position).getType() == Fluids.WATER)
                .setValue(FACING, place.getClickedFace());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collision) {
        return switch (state.getValue(FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState block) {
        return RenderShape.MODEL;
    }
}
