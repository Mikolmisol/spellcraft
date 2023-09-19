package mikolmisol.spellcraft.part_renderers;

import mikolmisol.spellcraft.parts.Part;
import org.jetbrains.annotations.NotNull;

public interface PartRenderer<T extends Part> {

    void render(@NotNull T part);
}
