package mikolmisol.spellcraft.blocks;

import mikolmisol.spellcraft.block_entities.SpellcraftingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SpellCraftingTableBlock extends SpellcraftBlockWithBlockEntity {
    public SpellCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState block, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            final var blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof SpellcraftingTableBlockEntity spellcraftingTable) {
                player.openMenu(spellcraftingTable);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpellcraftingTableBlockEntity(pos, state);
    }
}
