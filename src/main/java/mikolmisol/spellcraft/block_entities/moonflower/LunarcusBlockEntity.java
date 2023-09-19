package mikolmisol.spellcraft.block_entities.moonflower;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public final class LunarcusBlockEntity extends AbstractManaProducingFlowerBlockEntity {
    public LunarcusBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
