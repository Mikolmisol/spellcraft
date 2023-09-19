package mikolmisol.spellcraft.spells.targets;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record TargetType<T>(@Nullable ResourceLocation identifier) {
}
