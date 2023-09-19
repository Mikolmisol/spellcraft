package mikolmisol.spellcraft.spells.targets;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.spells.targets.impl.TargetsImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface Targets {
    static Targets of(final @NotNull List<Target<?>> targets) {
        return new TargetsImpl(targets);
    }

    static Targets of(final @NotNull Target<?> target) {
        return new TargetsImpl(Collections.singletonList(target));
    }

    @NotNull List<Target<?>> getAll();

    @SuppressWarnings("unchecked")
    default <E> @NotNull List<Target<E>> getAllOfType(final @NotNull TargetType<E> type) {
        final List<Target<E>> result = Lists.newArrayList();

        for (final var target : getAll()) {
            if (target.getType().equals(type)) {
                result.add((Target<E>) target);
            }
        }

        return result;
    }
}
