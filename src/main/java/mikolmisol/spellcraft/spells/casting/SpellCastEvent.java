package mikolmisol.spellcraft.spells.casting;

import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.impl.EntityBackedSpellCastEvent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A SpellCastEvent is a ticker that tracks and automatically increments
 * the amount of Mana expended when casting a Spell once per tick,
 * and invokes {@link Spell#cast(Caster, Level)} when a sufficient amount of Mana
 * has been expended.
 * <p>
 * Anything that can perform logic once per tick can be a SpelLCastEvent.
 * The default implementation is the {@link EntityBackedSpellCastEvent}.
 */
public interface SpellCastEvent {

    void failSpellCast();

    double getSpellCastingProgress();

    @FunctionalInterface
    interface Constructor {
        @NotNull SpellCastEvent create(@NotNull Spell spell, @NotNull Caster caster, @NotNull Level level);
    }
}
