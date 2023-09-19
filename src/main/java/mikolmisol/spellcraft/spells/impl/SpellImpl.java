package mikolmisol.spellcraft.spells.impl;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import mikolmisol.spellcraft.spells.casting.impl.EntityBackedSpellCastEvent;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.EffectNode;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifierHandlers;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.shapes.ShapeNode;
import mikolmisol.spellcraft.util.JavaUtil;
import mikolmisol.spellcraft.util.SpellUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

@EqualsAndHashCode
public final class SpellImpl implements Spell {

    private static final String TAG_SPELL_SHAPE_NODE = "SpellcraftSpellShapeNode";

    private static final String TAG_SPELL_NAME = "SpellcraftSpellName";

    @Getter
    private final @NotNull ShapeNode shape;

    @Getter
    private final Component name;

    @Getter
    private final int decimalColor;

    @Getter
    @Setter
    private double cost;

    private final @NotNull SpellCastEvent.Constructor constructor;

    public SpellImpl(@NotNull ShapeNode shape, @Nullable Component name, @NotNull SpellCastEvent.Constructor constructor) {
        this.shape = shape;
        this.constructor = constructor;

        this.cost = shape.getCost();

        if (name != null) {
            this.name = name;
        } else {
            this.name = SpellUtil.getDefaultNameForSpell(this);
        }

        decimalColor = shape.getDecimalColor();
    }

    public SpellImpl(@NotNull ShapeNode shape, @Nullable Component name) {
        this(shape, name, EntityBackedSpellCastEvent::create);
    }

    public static @Nullable Spell fromTag(CompoundTag tag) {
        final var shape = ShapeNode.fromTag(tag);

        if (shape == null) {
            return null;
        }

        final var name = Component.Serializer.fromJson(tag.getString(TAG_SPELL_NAME));

        return Spell.of(shape, name);
    }

    public static CompoundTag toTag(Spell spell) {
        final var tag = new CompoundTag();

        final var name = Component.Serializer.toJson(spell.getName());
        tag.putString(TAG_SPELL_NAME, name);

        return tag;
    }

    @Override
    public void cast(@NotNull Caster caster, @NotNull Level level) {
        shape.getShape().cast(this, caster, level);
    }

    @Override
    public @NotNull SpellCastEvent.Constructor getSpellCastEventConstructor() {
        return constructor;
    }
}
