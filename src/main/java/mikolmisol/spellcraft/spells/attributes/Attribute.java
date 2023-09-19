package mikolmisol.spellcraft.spells.attributes;

import org.jetbrains.annotations.NotNull;

public interface Attribute {
    @NotNull AttributeType<?> getType();
}
