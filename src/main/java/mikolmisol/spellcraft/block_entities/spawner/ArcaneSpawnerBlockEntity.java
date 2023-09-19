package mikolmisol.spellcraft.block_entities.spawner;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class ArcaneSpawnerBlockEntity extends SpellcraftBlockEntity {

    public ArcaneSpawnerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.ARCANE_SPAWNER, blockPos, blockState);
    }
}
