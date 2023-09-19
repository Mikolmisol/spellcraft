package mikolmisol.spellcraft.spells.attributes.impl;

import lombok.Getter;
import lombok.Setter;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class GravityAttribute implements Attribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "gravity");

    @Getter
    @Setter
    private double gravity;

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.GRAVITY;
    }
}
