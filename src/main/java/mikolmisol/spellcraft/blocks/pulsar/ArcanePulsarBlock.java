package mikolmisol.spellcraft.blocks.pulsar;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.pulsar.ArcanePulsarBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcanePulsarBlock extends SpellcraftBlockWithBlockEntity {
    public static int LIT_DURATION_TICKS = 20;

    public static BooleanProperty LIT = BlockStateProperties.LIT;

    public ArcanePulsarBlock(Properties properties) {
        super(properties.lightLevel(ArcanePulsarBlock::getLightLevelForState));
        registerDefaultState(defaultBlockState().setValue(LIT, false));
    }

    private static int getLightLevelForState(BlockState block) {
        if (block.getValue(LIT)) {
            return 15;
        }

        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void neighborChanged(BlockState block, Level level, BlockPos position, Block type, BlockPos blockPos2, boolean bl) {
        if (level.getBestNeighborSignal(position) < 1) {
            return;
        }

        final var blockEntity = level.getBlockEntity(position);
        if (blockEntity instanceof ArcanePulsarBlockEntity pulsar) {
            pulsar.emanateSpell(level);
            glow(block, level, position);
        }

        super.neighborChanged(block, level, position, type, blockPos2, bl);
    }

    private void glow(BlockState block, Level level, BlockPos position) {
        level.setBlock(position, block.setValue(LIT, true), 3);
        level.updateNeighborsAt(position, this);
        level.gameEvent(null, GameEvent.BLOCK_ACTIVATE, position);
        level.scheduleTick(position, this, LIT_DURATION_TICKS);
    }

    @Override
    public void tick(BlockState block, ServerLevel level, BlockPos position, RandomSource random) {
        if (!block.getValue(LIT)) {
            return;
        }

        if (level.getBestNeighborSignal(position) > 0) {
            return;
        }

        level.setBlock(position, block.setValue(LIT, false), 3);
        level.updateNeighborsAt(position, this);
        level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, position);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcanePulsarBlockEntity(position, block);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getClientTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                SpellcraftBlockEntityTypes.ARCANE_PULSAR,
                ArcanePulsarBlockEntity::clientTick
        );
    }
}
