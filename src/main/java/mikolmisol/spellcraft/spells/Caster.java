package mikolmisol.spellcraft.spells;

import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Caster {
    /**
     * Return the caster's ManaStorage from which Mana will be
     * subtracted each tick when casting a spell.
     */
    @Nullable ManaStorage getManaStorage();

    /**
     * Return the position from which the spell will finally be cast.
     * For example, this is a player's hand or an Arcane Coil's tip.
     */
    @NotNull Vec3 getSpellCastingPosition();

    void spellcraft_startCasting(@NotNull Spell spell, @NotNull Level level);

    boolean spellcraft_isCasting();

    void spellcraft_stopCasting();

    @ApiStatus.Internal
    void spellcraft_removeSpellCastEventReference();

    /**
     * @return the casting speed of this caster as Mana per tick.
     */
    default double spellcraft_getChanneledManaPerTick() {
        return 1;
    }

    /**
     * @return The multiplier with which the cost of a spell will be
     * multiplied during spell casting.
     */
    default double spellcraft_getSpellCostMultiplier() {
        return 1;
    }

    @ApiStatus.NonExtendable
    default @Nullable Entity asEntity() {
        if (this instanceof Entity entity) {
            return entity;
        }

        return null;
    }
}
