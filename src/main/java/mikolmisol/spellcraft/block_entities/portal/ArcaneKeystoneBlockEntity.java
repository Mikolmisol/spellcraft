package mikolmisol.spellcraft.block_entities.portal;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class ArcaneKeystoneBlockEntity extends SpellcraftBlockEntity {
    public ArcaneKeystoneBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.ARCANE_KEYSTONE, blockPos, blockState);
    }
}
