package mikolmisol.spellcraft.entities.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import mikolmisol.spellcraft.util.ManaUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellCasterMonster extends Monster implements Caster {

    protected @NotNull ManaStorage mana = ManaStorage.creative();

    protected @Nullable SpellCastEvent spellCastEvent;

    protected @NotNull List<@NotNull Spell> spellRepertoire = Lists.newArrayList();

    private double channeledManaPerTick = Caster.super.spellcraft_getChanneledManaPerTick();

    private double manaRegenerationPerTick;

    public SpellCasterMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    protected abstract double getMaximumManaForDifficulty(@NotNull DifficultyInstance difficulty);

    protected abstract double getManaRegenerationPerTickForDifficulty(@NotNull DifficultyInstance difficulty);

    protected abstract double getChanneledManaForDifficulty(@NotNull DifficultyInstance difficulty);

    protected abstract @NotNull List<@NotNull Spell> getSpellRepertoireForDifficulty(@NotNull DifficultyInstance difficulty);

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(randomSource, difficulty);

        final var maximumManaForDifficulty = getMaximumManaForDifficulty(difficulty);

        mana = ManaStorage.of(
                maximumManaForDifficulty,
                maximumManaForDifficulty
        );

        channeledManaPerTick = getChanneledManaForDifficulty(difficulty);

        manaRegenerationPerTick = getManaRegenerationPerTickForDifficulty(difficulty);

        spellRepertoire = getSpellRepertoireForDifficulty(difficulty);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            super.tick();
            return;
        }

        if (!isAlive()) {
            super.tick();
            return;
        }

        if (isNoAi()) {
            super.tick();
            return;
        }

        ManaUtil.insertManaAndDiscardOverflow(manaRegenerationPerTick, mana);

        super.tick();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        mana.toTag(tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        mana = ManaStorage.fromTag(tag);
    }

    @Override
    public final @Nullable ManaStorage getManaStorage() {
        return mana;
    }

    @Override
    public final void spellcraft_startCasting(@NotNull Spell spell, @NotNull Level level) {
        spellCastEvent = spell.getSpellCastEventConstructor().create(spell, this, level);
    }

    @Override
    public void spellcraft_stopCasting() {
        if (spellcraft_isCasting()) {
            spellCastEvent.failSpellCast();
        }
    }

    @Override
    public final boolean spellcraft_isCasting() {
        return spellCastEvent != null;
    }

    @Override
    public final void spellcraft_removeSpellCastEventReference() {
        spellCastEvent = null;
    }

    @Override
    public final double spellcraft_getChanneledManaPerTick() {
        return channeledManaPerTick;
    }
}
