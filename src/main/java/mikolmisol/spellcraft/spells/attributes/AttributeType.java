package mikolmisol.spellcraft.spells.attributes;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record AttributeType<T extends Attribute>(@NotNull ResourceLocation identifier) {
}
