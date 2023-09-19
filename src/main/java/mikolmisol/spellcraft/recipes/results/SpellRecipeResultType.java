package mikolmisol.spellcraft.recipes.results;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SpellRecipeResultType<V>(
        @NotNull ResourceLocation identifier,
        @NotNull SpellRecipeResultSerializer<V, SpellRecipeResult<V>> serializer
) {

}
