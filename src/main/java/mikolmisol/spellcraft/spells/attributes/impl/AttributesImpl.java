package mikolmisol.spellcraft.spells.attributes.impl;

import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public record AttributesImpl(
        @NotNull Map<AttributeType<? extends Attribute>, Attribute> attributes) implements Attributes {
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Attribute> @Nullable T get(@NotNull AttributeType<T> type) {
        return (T) attributes.get(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Attribute> @NotNull T getOrCreate(@NotNull AttributeType<T> type, @NotNull Supplier<@NotNull T> constructor) {
        return (T) attributes.computeIfAbsent(
                type,
                key -> constructor.get()
        );
    }

    @Override
    public <T extends Attribute> void create(@NotNull AttributeType<T> type, @NotNull T attribute) {
        attributes.putIfAbsent(type, attribute);
    }
}
