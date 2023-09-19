package mikolmisol.spellcraft.spells.targets.impl;

import mikolmisol.spellcraft.spells.targets.Target;
import mikolmisol.spellcraft.spells.targets.TargetType;
import org.jetbrains.annotations.NotNull;

public record TargetImpl<T>(@NotNull T value, @NotNull TargetType type) implements Target<T> {
    @Override
    public @NotNull T getValue() {
        return value;
    }

    @Override
    public @NotNull TargetType getType() {
        return type;
    }
}
