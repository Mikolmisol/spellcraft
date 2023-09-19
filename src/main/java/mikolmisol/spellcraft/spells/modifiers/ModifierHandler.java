package mikolmisol.spellcraft.spells.modifiers;

import mikolmisol.spellcraft.spells.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public interface ModifierHandler<Modifee> {
    void modifyAttributes(@NotNull Modifee modifee, @NotNull Attributes attributes);
}
