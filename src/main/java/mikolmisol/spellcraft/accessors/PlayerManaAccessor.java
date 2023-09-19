package mikolmisol.spellcraft.accessors;

public interface PlayerManaAccessor {
    void spellcraft_setManaAmount(double amount);

    void spellcraft_setManaCapacity(double capacity);

    void spellcraft_setSpellCastingProgress(double progress);

    void spellcraft_setSpellCastingTotal(double total);
}
