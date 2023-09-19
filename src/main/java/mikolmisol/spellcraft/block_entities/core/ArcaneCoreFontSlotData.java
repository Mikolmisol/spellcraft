package mikolmisol.spellcraft.block_entities.core;

import lombok.Getter;
import mikolmisol.spellcraft.block_entities.font.ArcaneFont;
import org.jetbrains.annotations.NotNull;

public final class ArcaneCoreFontSlotData extends ArcaneCoreSlotData {
    @Getter
    private final @NotNull ArcaneFont font;

    public ArcaneCoreFontSlotData(@NotNull ArcaneCoreSlotDataType type, @NotNull ArcaneFont font) {
        super(type);
        this.font = font;
    }
}
