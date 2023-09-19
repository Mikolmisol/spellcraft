package mikolmisol.spellcraft.recipes.ingredients;

import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientSerializer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SpellRecipeIngredientType<V>(
        @NotNull ResourceLocation identifier,
        @NotNull SpellRecipeIngredientSerializer<V, SpellRecipeIngredient<V>> serializer
) {

}
