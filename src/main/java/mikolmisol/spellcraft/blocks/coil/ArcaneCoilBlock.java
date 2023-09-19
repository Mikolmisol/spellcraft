package mikolmisol.spellcraft.blocks.coil;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.coil.ArcaneCoilBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import mikolmisol.spellcraft.util.GeomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public final class ArcaneCoilBlock extends SpellcraftBlockWithBlockEntity {
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public ArcaneCoilBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOP);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        if (!block.getValue(TOP)) {
            return null;
        }

        return new ArcaneCoilBlockEntity(position, block);
    }

    @Override
    public boolean canSurvive(BlockState block, LevelReader level, BlockPos position) {
        if (block.getValue(TOP)) {
            final var below = level.getBlockState(position.below());
            return below.is(this) && !below.getValue(TOP);
        }

        return true;
    }

    @Override
    public BlockState updateShape(BlockState block, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos position, BlockPos blockPos2) {
        if (!canSurvive(block, level, position)) {
            return Blocks.AIR.defaultBlockState();
        }

        if (!block.getValue(TOP)) {
            final var above = level.getBlockState(position.above());
            if (!(above.is(this) && above.getValue(TOP))) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(block, direction, blockState2, level, position, blockPos2);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext place) {
        if (GeomUtil.isBlockPositionAboveWithinBuildHeightAndReplaceable(place)) {
            return defaultBlockState().setValue(TOP, false);
        }

        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos position, BlockState block, @Nullable LivingEntity placer, ItemStack item) {
        level.setBlock(position.above(), block.setValue(TOP, true), 3);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState block, BlockEntityType<T> type) {
        if (!block.getValue(TOP)) {
            return null;
        }

        return createTickerHelper(
                type,
                SpellcraftBlockEntityTypes.ARCANE_COIL,
                ArcaneCoilBlockEntity::tick
        );
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter blocks, BlockPos position) {
        if (state.getValue(TOP)) {
            return Shapes.box(-0.5, -1.5, -0.5, 0.5, 0.5, 0.5);
        }
        return Shapes.box(-0.5, -0.5, -0.5, 0.5, 1.5, 0.5);
    }
}
