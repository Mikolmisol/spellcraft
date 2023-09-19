package mikolmisol.spellcraft.recipes;

import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@FunctionalInterface
public interface SpellRecipeConstructor {
    @NotNull SpellRecipe construct(@NotNull ResourceLocation identifier, @NotNull List<SpellRecipeIngredient<?>> ingredients, @NotNull Set<SpellRecipeResult<?>> results, double cost);
}
