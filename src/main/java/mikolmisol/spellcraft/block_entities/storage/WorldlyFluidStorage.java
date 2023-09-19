package mikolmisol.spellcraft.block_entities.storage;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public interface WorldlyFluidStorage {
    @Nullable Storage<FluidVariant> getFluidStorageForDirection(Direction direction);
}
