package mikolmisol.spellcraft.spells.shapes;

import lombok.*;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.effects.EffectNode;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifierHandlers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShapeNode {

    private static final Random RANDOM = new Random();

    private static final String TAG_SPELL_SHAPE = "SpellcraftSpellShape";

    private static final String TAG_SPELL_SECONDARY_SHAPE = "SpellcraftSpellSecondaryShape";

    private static final String TAG_SPELL_EFFECTS = "SpellcraftSpellEffects";

    private static final String TAG_SPELL_MODIFIERS = "SpellcraftSpellModifiers";

    @Getter
    private final @NotNull Shape shape;

    @Getter
    private final @Nullable ShapeNode secondaryShape;

    @Getter
    private final @NotNull List<@NotNull EffectNode> effects;

    @Getter
    private final @NotNull List<@NotNull Modifier> modifiers;

    @Getter
    private final Attributes attributes = Attributes.create();

    @Getter
    @Setter(AccessLevel.PUBLIC)
    public double cost;

    public static ShapeNode of(
            @NotNull Shape shape,
            @Nullable ShapeNode secondaryShape,
            @NotNull List<@NotNull EffectNode> effects,
            @NotNull List<@NotNull Modifier> modifiers
    ) {
        final var shapeNode = new ShapeNode(
                shape,
                secondaryShape,
                effects.stream().sorted().toList(),
                modifiers.stream().sorted().toList()
        );

        shapeNode.cost = shape.getCost();

        if (secondaryShape != null) {
            shapeNode.cost += secondaryShape.getCost();
        }

        for (final var effect : effects) {
            shapeNode.cost += effect.getCost();
        }

        shapeNode.modifyAttributes();

        for (final var effectNode : effects) {
            effectNode.modifyAttributes();
        }

        return shapeNode;
    }

    private void modifyAttributes() {
        for (final var modifier : modifiers) {
            final var handler = SpellcraftModifierHandlers.getShapeHandler(shape, modifier);

            if (handler == null) {
                continue;
            }

            handler.modifyAttributes(this, attributes);
        }
    }

    public int getDecimalColor() {
        if (effects.size() > 0) {
            return effects.get(RANDOM.nextInt(0, effects.size())).getEffect().getDecimalColor();
        }
        else if (secondaryShape != null && secondaryShape.effects.size() > 0) {
            return secondaryShape.effects.get(RANDOM.nextInt(0, secondaryShape.effects.size())).getEffect().getDecimalColor();
        }

        return 0;
    }

    public static @Nullable ShapeNode fromTag(@NotNull CompoundTag tag, boolean parsingSecondaryShape) {
        if (!tag.contains(TAG_SPELL_SHAPE)) {
            return null;
        }

        if (!tag.contains(TAG_SPELL_EFFECTS)) {
            return null;
        }

        if (!tag.contains(TAG_SPELL_MODIFIERS)) {
            return null;
        }

        final var shapeString = tag.getString(TAG_SPELL_SHAPE);
        final var shape = SpellcraftRegistries.SHAPE.get(new ResourceLocation(shapeString));

        if (shape == null) {
            return null;
        }

        var secondaryShape = (ShapeNode) null;

        if (parsingSecondaryShape && tag.contains(TAG_SPELL_SECONDARY_SHAPE)) {
            secondaryShape = ShapeNode.fromTag(tag.getCompound(TAG_SPELL_SECONDARY_SHAPE), true);
        }

        final var effectsList = tag.getList(TAG_SPELL_EFFECTS, Tag.TAG_COMPOUND);
        final var effects = new ArrayList<EffectNode>(effectsList.size());

        for (var index = 0; index < effectsList.size(); index += 1) {
            final var effectTag = effectsList.getCompound(index);
            final var effect = EffectNode.fromTag(effectTag);

            if (effect != null) {
                effects.add(effect);
            }
        }

        final var modifiersList = tag.getList(TAG_SPELL_MODIFIERS, Tag.TAG_STRING);
        final var modifiers = new ArrayList<Modifier>(modifiersList.size());

        for (var index = 0; index < modifiersList.size(); index += 1) {
            final var modifier = SpellcraftRegistries.MODIFIER.get(new ResourceLocation(modifiersList.getString(index)));

            if (modifier != null) {
                modifiers.add(modifier);
            }
        }

        return of(shape, secondaryShape, effects, modifiers);
    }

    public static @Nullable ShapeNode fromTag(@NotNull CompoundTag tag) {
        return fromTag(tag, false);
    }
}
