package mikolmisol.spellcraft.spells.casting.impl;

import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import mikolmisol.spellcraft.util.ManaUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class EntityBackedSpellCastEvent extends Entity implements SpellCastEvent {

    private Spell spell;

    private Caster caster;

    private double expendedMana;

    public EntityBackedSpellCastEvent(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public static EntityBackedSpellCastEvent create(
            @NotNull Spell spell,
            @NotNull Caster caster,
            @NotNull Level level
    ) {
        final var self = new EntityBackedSpellCastEvent(SpellcraftEntityTypes.SPELL_CAST_EVENT, level);

        self.spell = spell;
        self.caster = caster;

        self.setPos(caster.getSpellCastingPosition());
        level.addFreshEntity(self);

        return self;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    public void tick() {
        setPos(caster.getSpellCastingPosition());
        checkBelowWorld();

        final var mana = caster.getManaStorage();

        if (mana == null) {
            failSpellCast();
            return;
        }

        final var actualCost = spell.getCost() * caster.spellcraft_getSpellCostMultiplier();
        final var castingSpeed = caster.spellcraft_getChanneledManaPerTick();
        final var costChunk = Math.min(castingSpeed, actualCost - expendedMana);

        if (!ManaUtil.tryConsumeMana(costChunk, mana)) {
            failSpellCast();
        }

        expendedMana += costChunk;

        if (expendedMana >= actualCost) {
            expendedMana = 0;
            spell.cast(caster, level);
            discard();
        }

        firstTick = false;
    }

    @Override
    public void failSpellCast() {
        caster.spellcraft_removeSpellCastEventReference();
        discard();
    }

    @Override
    public double getSpellCastingProgress() {
        return expendedMana / (spell.getCost() * caster.spellcraft_getSpellCostMultiplier());
    }
}
