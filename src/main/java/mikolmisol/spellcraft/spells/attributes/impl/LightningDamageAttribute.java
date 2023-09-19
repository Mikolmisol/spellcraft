package mikolmisol.spellcraft.spells.attributes.impl;

import lombok.AllArgsConstructor;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class LightningDamageAttribute extends DamageAttribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "lightning_damage");

    public LightningDamageAttribute(float damage) {
        super(damage);
    }

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.LIGHTNING_DAMAGE;
    }

}
