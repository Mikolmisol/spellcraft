package mikolmisol.spellcraft.blocks.catalyst;

import mikolmisol.spellcraft.block_entities.catalyst.ArcaneCatalystBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class ArcaneCatalystBlock extends SpellcraftBlockWithBlockEntity {
    public ArcaneCatalystBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ArcaneCatalystBlockEntity(position, block);
    }
}
