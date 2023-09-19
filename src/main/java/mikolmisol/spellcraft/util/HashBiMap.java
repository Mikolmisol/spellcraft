package mikolmisol.spellcraft.util;

import com.google.common.collect.Maps;
import net.minecraft.data.models.blockstates.VariantProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiFunction;

public final class HashBiMap<Key1, Key2, Value> {

    private final Map<Key1, Map<Key2, Value>> backingMap = Maps.newHashMap();

    public void put(@NotNull Key1 key1, @NotNull Key2 key2, @NotNull Value value) {
        final var innerMap = backingMap.computeIfAbsent(
                key1,
                key -> Maps.newHashMap()
        );

        innerMap.put(key2, value);
    }

    public @Nullable Value get(@NotNull Key1 key1, @NotNull Key2 key2) {
        final var innerMap = backingMap.get(key1);

        if (innerMap == null) {
            return null;
        }

        return innerMap.get(key2);
    }

    public @NotNull Value computeIfAbsent(@NotNull Key1 key1, @NotNull Key2 key2, @NotNull BiFunction<@NotNull Key1, @NotNull Key2, @NotNull Value> computation) {
        var value = get(key1, key2);

        if (value != null) {
            return value;
        }

        value = computation.apply(key1, key2);

        put(key1, key2, value);
        return value;
    }
}
