package mikolmisol.spellcraft.block_entities.turret;

import mikolmisol.spellcraft.block_entities.storage.WorldlyManaStorage;
import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneTurretBlockEntity extends BlockEntity implements WorldlyManaStorage {
    public ArcaneTurretBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public @Nullable ManaStorage getManaStorageForDirection(@NotNull Direction direction) {
        return null;
    }

    @Override
    public @Nullable ManaStorage getManaStorage() {
        return null;
    }
}
