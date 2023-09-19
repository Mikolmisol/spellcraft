package mikolmisol.spellcraft.spells.attributes.impl;

import lombok.Getter;
import lombok.Setter;
import mikolmisol.spellcraft.spells.attributes.Attribute;

public abstract class DamageAttribute implements Attribute {
    @Getter
    @Setter
    protected float damage;

    public DamageAttribute(float damage) {
        this.damage = damage;
    }
}
