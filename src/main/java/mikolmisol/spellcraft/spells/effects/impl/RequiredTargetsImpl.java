package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.TargetType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@ApiStatus.Internal
public final class RequiredTargetsImpl implements RequiredTargets {
    private final List<TargetType> types;
    private final Mode mode;

    private RequiredTargetsImpl(final @NotNull List<TargetType> types, final @NotNull Mode mode) {
        this.types = types;
        this.mode = mode;
    }

    public static RequiredTargets allOf(final @NotNull TargetType... types) {
        return new RequiredTargetsImpl(Arrays.asList(types), Mode.ALL_OF);
    }

    public static RequiredTargets atLeastOneOf(final @NotNull TargetType... types) {
        return new RequiredTargetsImpl(Arrays.asList(types), Mode.AT_LEAST_ONE_OF);
    }

    @Override
    public boolean areSatisfiedBy(@NotNull ProvidedTargets providedTargets) {
        final var allProvidedTargets = providedTargets.getAll();

        return switch (mode) {
            case ALL_OF -> {
                for (final var type : types) {
                    if (!allProvidedTargets.contains(type)) {
                        yield false;
                    }
                }

                yield true;
            }
            case AT_LEAST_ONE_OF -> {
                for (final var type : types) {
                    if (allProvidedTargets.contains(type)) {
                        yield true;
                    }
                }

                yield false;
            }
        };
    }

    private enum Mode {
        ALL_OF,
        AT_LEAST_ONE_OF
    }
}
