package mikolmisol.spellcraft.blocks.core;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.core.ArcaneCoreBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneCoreBlock extends SpellcraftBlockWithBlockEntity {
    public ArcaneCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcaneCoreBlockEntity(position, block);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState block, BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                SpellcraftBlockEntityTypes.ARCANE_CORE,
                ArcaneCoreBlockEntity::tick
        );
    }
}
