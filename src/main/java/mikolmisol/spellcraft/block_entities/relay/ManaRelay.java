package mikolmisol.spellcraft.block_entities.relay;

import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ManaRelay {
    void passOnMana(@NotNull ManaStorage mana, double totalManaLoss, @NotNull Level level, @NotNull Set<BlockPos> visitedBlocks);
}
