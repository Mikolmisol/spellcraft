package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.damage.SpellDamageSource;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.damage.SpellcraftDamageTypes;
import mikolmisol.spellcraft.spells.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DamageSources.class)
public abstract class SpellcraftDamageSourcesMixin implements SpellcraftDamageSources {
    @Shadow
    @Final
    public Registry<DamageType> damageTypes;

    @Override
    public @NotNull SpellDamageSource spellcraft_arcane(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target) {
        return spellcraft_createDamageSource(
                SpellcraftDamageTypes.ARCANE,
                spell,
                caster,
                target
        );
    }

    @Override
    public @NotNull SpellDamageSource spellcraft_frost(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target) {
        return spellcraft_createDamageSource(
                SpellcraftDamageTypes.FROST,
                spell,
                caster,
                target
        );
    }

    @Override
    public @NotNull SpellDamageSource spellcraft_fire(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target) {
        return spellcraft_createDamageSource(
                SpellcraftDamageTypes.FIRE,
                spell,
                caster,
                target
        );
    }

    @Override
    public @NotNull SpellDamageSource spellcraft_lightning(@NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target) {
        return spellcraft_createDamageSource(
                SpellcraftDamageTypes.LIGHTNING,
                spell,
                caster,
                target
        );
    }

    @Unique
    private SpellDamageSource spellcraft_createDamageSource(@NotNull ResourceKey<DamageType> type, @NotNull Spell spell, @Nullable Entity caster, @Nullable Entity target) {
        return new SpellDamageSource(
                damageTypes.getHolderOrThrow(type),
                target,
                caster,
                spell
        );
    }
}
