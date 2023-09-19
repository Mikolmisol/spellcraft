package mikolmisol.spellcraft.spells.attributes.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ClipContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public final class BlockClipContextAttribute implements Attribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "block_clip_context");

    @Getter
    @Setter
    private @Nullable ClipContext.Block blockClippingBehavior;

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.BLOCK_CLIP_CONTEXT;
    }
}
