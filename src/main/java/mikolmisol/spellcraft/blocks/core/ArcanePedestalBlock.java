package mikolmisol.spellcraft.blocks.core;

import mikolmisol.spellcraft.block_entities.pedestal.ArcanePedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ArcanePedestalBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE;

    public ArcanePedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            final var blockEntity = level.getBlockEntity(pos);

            if (!(blockEntity instanceof final ArcanePedestalBlockEntity pedestal)) {
                return InteractionResult.PASS;
            }

            final var handItem = player.getItemInHand(hand);
            final var pedestalItem = pedestal.getItem(0);

            if (handItem.isEmpty()) {
                player.getInventory().placeItemBackInInventory(pedestal.removeItem(0, pedestalItem.getMaxStackSize()));
                return InteractionResult.CONSUME;
            } else if (pedestalItem.isEmpty()) {
                pedestal.setItem(0, handItem.split(handItem.getMaxStackSize()));
                return InteractionResult.CONSUME;
            } else if (ItemStack.isSameItemSameTags(handItem, pedestalItem)) {
                final var pedestalItemCount = pedestalItem.getCount();
                final var handItemCount = handItem.getCount();
                final var totalCount = pedestalItemCount + handItemCount;
                final var maxStackSize = pedestalItem.getMaxStackSize();

                if (totalCount <= maxStackSize) {
                    final var finalCount = pedestalItemCount + handItemCount;
                    final var item = handItem.split(maxStackSize);
                    item.setCount(finalCount);
                    pedestal.setItem(0, item);

                    return InteractionResult.CONSUME;
                }

                // totalCount > handItemMaxStackSize
                final var countThatFits = totalCount - maxStackSize;
                final var finalCount = pedestalItemCount + countThatFits;
                final var item = handItem.split(countThatFits);
                item.setCount(finalCount);
                pedestal.setItem(0, item);

                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcanePedestalBlockEntity(position, block);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    static {
        SHAPE = Shapes.join(
                Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.19, 0.9375),
                Shapes.join(
                        Shapes.box(0.185, 0, 0.185, 0.815, 0.315, 0.815),
                        Shapes.join(
                                Shapes.box(0.25, 0, 0.25, 0.75, 0.7, 0.75),
                                Shapes.box(0.065, 0.625, 0.065, 0.935, 0.94, 0.935),
                                BooleanOp.NOT_SAME
                        ),
                        BooleanOp.NOT_SAME
                ),
                BooleanOp.NOT_SAME
        );
    }
}
