package mikolmisol.spellcraft.spells.attributes.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class HealingAttribute implements Attribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "healing");

    @Getter
    @Setter
    private float healing;

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.HEALING;
    }
}
