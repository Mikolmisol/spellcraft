package mikolmisol.spellcraft.spells.attributes;

import com.google.common.collect.Maps;
import mikolmisol.spellcraft.spells.attributes.impl.AttributesImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface Attributes {
    static Attributes create() {
        return new AttributesImpl(Maps.newHashMap());
    }

    <T extends Attribute> @Nullable T get(@NotNull AttributeType<T> type);

    <T extends Attribute> @NotNull T getOrCreate(@NotNull AttributeType<T> type, @NotNull Supplier<@NotNull T> constructor);

    <T extends Attribute> void create(@NotNull AttributeType<T> type, @NotNull T attribute);
}
