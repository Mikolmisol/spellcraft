package mikolmisol.spellcraft.block_entities.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public abstract class ArcaneCoreSlotData {
    @Getter
    @Setter
    @NotNull
    private ArcaneCoreSlotDataType type;
}
