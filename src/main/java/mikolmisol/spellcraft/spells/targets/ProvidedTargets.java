package mikolmisol.spellcraft.spells.targets;

import mikolmisol.spellcraft.spells.targets.impl.ProvidedTargetsImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public interface ProvidedTargets {
    static ProvidedTargets of(final @NotNull TargetType... targets) {
        return new ProvidedTargetsImpl(Arrays.asList(targets));
    }

    @NotNull List<TargetType> getAll();
}
