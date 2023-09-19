package mikolmisol.spellcraft.block_entities.storage;

import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface to be implemented by {@link net.minecraft.world.level.block.entity.BlockEntity}s,
 * objects that could contain Spellcraft's Energy.
 */
public interface WorldlyManaStorage {
    /**
     * @return the mana storage associated with this object
     * when the object is accessed from direction.
     * The storage does not have to support extraction or
     * insertion.
     */
    @Nullable ManaStorage getManaStorageForDirection(@NotNull Direction direction);

    @Nullable ManaStorage getManaStorage();
}
