package mikolmisol.spellcraft.block_entities.core;

import lombok.Getter;
import mikolmisol.spellcraft.block_entities.barrier.ArcaneHoldingCell;
import org.jetbrains.annotations.NotNull;

public final class ArcaneCoreHoldingCellSlotData extends ArcaneCoreSlotData {
    @Getter
    private final @NotNull ArcaneHoldingCell cell;

    public ArcaneCoreHoldingCellSlotData(@NotNull ArcaneCoreSlotDataType type, @NotNull ArcaneHoldingCell cell) {
        super(type);
        this.cell = cell;
    }
}
