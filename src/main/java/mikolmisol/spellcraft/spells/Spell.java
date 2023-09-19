package mikolmisol.spellcraft.spells;

import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import mikolmisol.spellcraft.spells.casting.impl.EntityBackedSpellCastEvent;
import mikolmisol.spellcraft.spells.effects.EffectNode;
import mikolmisol.spellcraft.spells.impl.SpellImpl;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.shapes.ShapeNode;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Spell {

    @NotNull ShapeNode getShape();

    double getCost();

    void setCost(double cost);

    @NotNull Component getName();

    void cast(@NotNull Caster caster, @NotNull Level level);

    default void playSuccessSound(@NotNull Caster caster, @NotNull Level level) {
    }

    default void playFailureSound(@NotNull Caster caster, @NotNull Level level) {
    }

    @NotNull SpellCastEvent.Constructor getSpellCastEventConstructor();

    int getDecimalColor();

    static Spell fromTag(CompoundTag tag) {
        return SpellImpl.fromTag(tag);
    }

    static CompoundTag toTag(Spell spell) {
        return SpellImpl.toTag(spell);
    }

    static @NotNull Spell of(@NotNull ShapeNode shape, @Nullable Component name) {
        return new SpellImpl(shape, name);
    }
}
