package mikolmisol.spellcraft.spells.targets.impl;

import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.TargetType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProvidedTargetsImpl implements ProvidedTargets {
    private final List<TargetType> types;

    public ProvidedTargetsImpl(final @NotNull List<TargetType> types) {
        this.types = types;
    }

    @Override
    public @NotNull List<TargetType> getAll() {
        return types;
    }
}
