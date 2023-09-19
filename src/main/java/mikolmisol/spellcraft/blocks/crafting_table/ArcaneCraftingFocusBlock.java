package mikolmisol.spellcraft.blocks.crafting_table;

import mikolmisol.spellcraft.block_entities.crafting_table.ArcaneCraftingFocusBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public final class ArcaneCraftingFocusBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE;

    static {
        SHAPE = Shapes.box(0.3, 0, 0.3, 0.7, 0.75, 0.7);
    }

    public ArcaneCraftingFocusBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcaneCraftingFocusBlockEntity(position, block);
    }

    @Override
    public RenderShape getRenderShape(BlockState block) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState block, LevelReader level, BlockPos position) {
        return level.getBlockState(position.below()).is(SpellcraftBlocks.ARCANE_WORKBENCH);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState block, BlockGetter blocks, BlockPos position) {
        return block.getFluidState().isEmpty();
    }

    @Override
    public BlockState updateShape(BlockState block, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos position, BlockPos blockPos2) {
        if (!canSurvive(block, level, position)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(block, direction, blockState2, level, position, blockPos2);
    }
}
