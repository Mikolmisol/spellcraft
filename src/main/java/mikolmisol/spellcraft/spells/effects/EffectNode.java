package mikolmisol.spellcraft.spells.effects;

import lombok.*;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifierHandlers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EffectNode implements Comparable<EffectNode> {
    private static final String TAG_SPELL_EFFECT = "SpellcraftSpellEffect";

    private static final String TAG_SPELL_MODIFIERS = "SpellcraftSpellModifiers";

    @Getter
    private final @NotNull Effect effect;

    @Getter
    private final @NotNull List<Modifier> modifiers;

    private final Attributes attributes = Attributes.create();

    @Getter
    @Setter
    private double cost;

    @Override
    public int compareTo(EffectNode node) {
        final var effect1 = getEffect();
        final var effect2 = node.getEffect();

        if (effect1.equals(effect2)) {
            return 0;
        }

        final var modifiers1 = getModifiers();
        final var modifiers2 = node.getModifiers();

        if (modifiers1.size() == 0 && modifiers2.size() == 0) {
            return effect1.compareTo(effect2);
        } else if (modifiers1.size() > modifiers2.size()) {
            return 1;
        } else if (modifiers1.size() < modifiers2.size()) {
            return -1;
        } else {
            final var firstModifier1 = modifiers1.get(0);
            final var firstModifier2 = modifiers2.get(0);

            return firstModifier1.compareTo(firstModifier2);
        }
    }

    public void modifyAttributes() {
        for (final var modifier : modifiers) {
            final var handler = SpellcraftModifierHandlers.getEffectHandler(effect, modifier);

            if (handler == null) {
                continue;
            }

            handler.modifyAttributes(this, attributes);
        }
    }

    public static EffectNode of(@NotNull Effect effect, @NotNull List<@NotNull Modifier> modifiers) {
        final var effectNode = new EffectNode(
                effect,
                modifiers.stream().sorted().toList()
        );

        effectNode.cost = effect.getCost();

        return effectNode;
    }

    public static @Nullable EffectNode fromTag(@NotNull CompoundTag tag) {
        if (!tag.contains(TAG_SPELL_EFFECT)) {
            return null;
        }

        if (!tag.contains(TAG_SPELL_MODIFIERS)) {
            return null;
        }

        final var effectString = tag.getString(TAG_SPELL_EFFECT);
        final var effect = SpellcraftRegistries.EFFECT.get(new ResourceLocation(effectString));

        if (effect == null) {
            return null;
        }

        final var modifiersList = tag.getList(TAG_SPELL_MODIFIERS, Tag.TAG_STRING);
        final var modifiers = new ArrayList<Modifier>(modifiersList.size());

        for (var index = 0; index < modifiersList.size(); index += 1) {
            final var modifier = SpellcraftRegistries.MODIFIER.get(new ResourceLocation(modifiersList.getString(index)));

            if (modifier != null) {
                modifiers.add(modifier);
            }
        }

        return of(effect, modifiers);
    }
}
