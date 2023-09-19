package mikolmisol.spellcraft.spells.attributes.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class FrostDamageAttribute extends DamageAttribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "frost_damage");

    public FrostDamageAttribute(float damage) {
        super(damage);
    }

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.FROST_DAMAGE;
    }

}
