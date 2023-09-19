package mikolmisol.spellcraft.block_entities.moonflower;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractManaProducingFlowerBlockEntity extends BlockEntity {
    private static final float MAXIMUM_OPEN = 0.75f;

    private float open;

    private float oldOpen;

    private boolean opening;

    private boolean closing;

    public AbstractManaProducingFlowerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos position, BlockState state, AbstractManaProducingFlowerBlockEntity flower) {

    }

    public static void clientTick(Level level, BlockPos position, BlockState state, AbstractManaProducingFlowerBlockEntity flower) {
        if (flower.opening) {
            if (level.isDay()) {
                flower.opening = false;
                flower.closing = true;
            } else {
                if (flower.open < MAXIMUM_OPEN) {
                    flower.oldOpen = flower.open;
                    flower.open += 0.01;
                } else {
                    flower.opening = false;
                }
            }
        } else if (flower.closing) {
            if (level.isDay()) {
                if (flower.open < 0) {
                    flower.closing = false;
                } else {
                    flower.oldOpen = flower.open;
                    flower.open -= 0.01;
                }
            } else {
                flower.opening = true;
                flower.closing = false;
            }
        }
    }
}
