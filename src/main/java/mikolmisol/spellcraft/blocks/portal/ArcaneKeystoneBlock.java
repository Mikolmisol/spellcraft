package mikolmisol.spellcraft.blocks.portal;

import mikolmisol.spellcraft.block_entities.portal.ArcaneKeystoneBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class ArcaneKeystoneBlock extends SpellcraftBlockWithBlockEntity {

    public ArcaneKeystoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArcaneKeystoneBlockEntity(blockPos, blockState);
    }
}
