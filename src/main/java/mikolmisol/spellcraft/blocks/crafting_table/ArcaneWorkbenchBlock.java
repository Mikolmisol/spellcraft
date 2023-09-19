package mikolmisol.spellcraft.blocks.crafting_table;

import mikolmisol.spellcraft.block_entities.crafting_table.ArcaneWorkbenchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public final class ArcaneWorkbenchBlock extends BaseEntityBlock {
    public ArcaneWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcaneWorkbenchBlockEntity(position, block);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(blockPos) instanceof ArcaneWorkbenchBlockEntity arcaneWorkbench) {
            player.openMenu(arcaneWorkbench);
        }

        return InteractionResult.CONSUME;
    }
}
