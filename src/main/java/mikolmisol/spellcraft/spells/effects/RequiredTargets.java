package mikolmisol.spellcraft.spells.effects;

import mikolmisol.spellcraft.spells.effects.impl.RequiredTargetsImpl;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.TargetType;
import org.jetbrains.annotations.NotNull;

public interface RequiredTargets {
    static RequiredTargets none() {
        return RequiredTargetsImpl.allOf();
    }

    /**
     * These requirements are only fulfilled by a Shape that provides the type.
     */
    static RequiredTargets just(final @NotNull TargetType<?> type) {
        return RequiredTargetsImpl.allOf(type);
    }

    /**
     * These requirements are only fulfilled by a Shape that provides every single
     * one of the types.
     */
    static RequiredTargets allOf(final @NotNull TargetType<?>... types) {
        if (types.length < 2) {
            throw new IllegalArgumentException("At least two TargetTypes must be provided.");
        }
        return RequiredTargetsImpl.allOf(types);
    }

    /**
     * These requirements are only fulfilled by a Shape that provides at least one
     * of them.
     */
    static RequiredTargets atLeastOneOf(final @NotNull TargetType<?>... types) {
        if (types.length < 2) {
            throw new IllegalArgumentException("At least two TargetTypes must be provided.");
        }
        return RequiredTargetsImpl.atLeastOneOf(types);
    }

    boolean areSatisfiedBy(final @NotNull ProvidedTargets providedTargets);
}
