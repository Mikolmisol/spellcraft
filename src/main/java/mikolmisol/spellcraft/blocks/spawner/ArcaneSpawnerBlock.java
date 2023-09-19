package mikolmisol.spellcraft.blocks.spawner;

import mikolmisol.spellcraft.block_entities.spawner.ArcaneSpawnerBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class ArcaneSpawnerBlock extends SpellcraftBlockWithBlockEntity {
    public ArcaneSpawnerBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArcaneSpawnerBlockEntity(blockPos, blockState);
    }
}
