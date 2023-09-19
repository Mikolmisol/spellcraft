package mikolmisol.spellcraft.spells.targets.impl;

import mikolmisol.spellcraft.spells.targets.Target;
import mikolmisol.spellcraft.spells.targets.Targets;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class TargetsImpl implements Targets {
    private final List<Target<?>> targets;

    public TargetsImpl(final @NotNull List<Target<?>> targets) {
        this.targets = targets;
    }

    @Override
    public @NotNull List<Target<?>> getAll() {
        return targets;
    }
}
