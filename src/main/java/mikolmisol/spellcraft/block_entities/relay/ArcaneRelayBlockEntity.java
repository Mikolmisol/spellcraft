package mikolmisol.spellcraft.block_entities.relay;

import com.google.common.collect.Sets;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static mikolmisol.spellcraft.blocks.relay.ArcaneRelayBlock.FACING;

public final class ArcaneRelayBlockEntity extends BlockEntity implements ManaRelay {
    private static final double MANA_LOSS_PER_RELAY = 0.1;

    private static final double MANA_TRANSFER_RATE_PER_TICK = 10;

    public float timer;

    public ArcaneRelayBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.MANA_RELAY, blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos position, BlockState state, ArcaneRelayBlockEntity entity) {
        final var visitedBlocks = Sets.<BlockPos>newHashSet();

        final var mana = entity.lookForManaInDirection(level, position, state.getValue(FACING).getOpposite(), visitedBlocks);
        if (mana == null) {
            return;
        }

        entity.passOnMana(mana, 0, level, visitedBlocks);
    }

    @Override
    public void passOnMana(@NotNull ManaStorage mana, double totalManaLoss, @NotNull Level level, @NotNull Set<BlockPos> visitedBlocks) {
        final var position = getBlockPos();

        if (visitedBlocks.contains(position)) {
            return;
        }

        visitedBlocks.add(position);

        totalManaLoss += MANA_LOSS_PER_RELAY;

        if (totalManaLoss >= MANA_TRANSFER_RATE_PER_TICK) {
        }
    }

    private @Nullable ManaStorage lookForManaInDirection(Level level, BlockPos pos, Direction direction, Set<BlockPos> visitedBlocks) {
        final var position = pos.relative(direction);

        if (visitedBlocks.contains(position)) {
            return null;
        }

        visitedBlocks.add(position);

        return ManaStorage.SIDED.find(
                level,
                position,
                direction
        );
    }
}
