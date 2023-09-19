package mikolmisol.spellcraft.block_entities.core;

import lombok.Getter;
import mikolmisol.spellcraft.block_entities.pedestal.ArcanePedestal;
import org.jetbrains.annotations.NotNull;

public final class ArcaneCorePedestalSlotData extends ArcaneCoreSlotData {
    @Getter
    private final @NotNull ArcanePedestal pedestal;

    public ArcaneCorePedestalSlotData(@NotNull ArcaneCoreSlotDataType type, @NotNull ArcanePedestal pedestal) {
        super(type);
        this.pedestal = pedestal;
    }
}
