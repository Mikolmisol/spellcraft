package mikolmisol.spellcraft.block_entities.barrier;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ArcaneHoldingCell {

    @Nullable Set<BlockPos> getProjectorPositionsIfComplete();
}
