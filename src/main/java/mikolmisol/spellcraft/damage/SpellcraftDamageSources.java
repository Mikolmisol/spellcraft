package mikolmisol.spellcraft.damage;

import mikolmisol.spellcraft.spells.Spell;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpellcraftDamageSources {
    static @NotNull SpellcraftDamageSources of(@NotNull Entity entity) {
        return (SpellcraftDamageSources) entity.damageSources();
    }

    @NotNull SpellDamageSource spellcraft_arcane(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target);

    @NotNull SpellDamageSource spellcraft_frost(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target);

    @NotNull SpellDamageSource spellcraft_fire(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target);

    @NotNull SpellDamageSource spellcraft_lightning(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target);
}
