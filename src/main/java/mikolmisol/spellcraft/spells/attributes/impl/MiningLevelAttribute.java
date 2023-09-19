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
public final class MiningLevelAttribute implements Attribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "mining_level");

    @Getter
    @Setter
    private int miningLevel;

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.MINING_LEVEL;
    }

    public interface MiningLevel {
        int HAND = -1;
        int WOOD = 0;
        int STONE = 1;
        int IRON = 2;
        int DIAMOND = 3;
        int NETHERITE = 4;
    }
}
