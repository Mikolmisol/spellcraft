package mikolmisol.spellcraft.spells.impl;

import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ManaCrystalCaster implements Caster {
    private @Nullable SpellCastEvent spellCastEvent;

    private final ManaStorage mana = ManaStorage.creative();

    private final BlockPos pos;

    public ManaCrystalCaster(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public @NotNull ManaStorage getManaStorage() {
        return mana;
    }

    @Override
    public @NotNull Vec3 getSpellCastingPosition() {
        return Vec3.atCenterOf(pos);
    }

    @Override
    public void spellcraft_startCasting(@NotNull Spell spell, @NotNull Level level) {
        spellCastEvent = spell.getSpellCastEventConstructor().create(spell, this, level);
    }

    @Override
    public boolean spellcraft_isCasting() {
        return spellCastEvent != null;
    }

    @Override
    public void spellcraft_stopCasting() {
        if (spellCastEvent != null) {
            spellCastEvent.failSpellCast();
        }
    }

    @Override
    public void spellcraft_removeSpellCastEventReference() {
        spellCastEvent = null;
    }

    @Override
    public double spellcraft_getChanneledManaPerTick() {
        return Double.MAX_VALUE;
    }

    @Override
    public double spellcraft_getSpellCostMultiplier() {
        return 0;
    }
}
