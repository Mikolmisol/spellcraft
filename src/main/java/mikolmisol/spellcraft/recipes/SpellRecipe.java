package mikolmisol.spellcraft.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import mikolmisol.spellcraft.recipes.crafters.SpellCrafter;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientType;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface SpellRecipe extends Recipe<SpellCrafter> {
    @NotNull List<SpellRecipeIngredient<?>> getAllIngredients();

    @SuppressWarnings("unchecked")
    default <V> @NotNull List<SpellRecipeIngredient<V>> getAllIngredientsOfType(final @NotNull SpellRecipeIngredientType<V> type) {
        final var ingredients = getAllIngredients();
        final var result = Lists.<SpellRecipeIngredient<V>>newArrayList();

        for (final var ingredient : ingredients) {
            if (ingredient.type().equals(type)) {
                result.add((SpellRecipeIngredient<V>) ingredient);
            }
        }

        return result;
    }

    @NotNull Set<SpellRecipeResult<?>> getAllResults();

    @SuppressWarnings("unchecked")
    default <V> @NotNull Set<SpellRecipeResult<V>> getAllResultsOfType(final @NotNull SpellRecipeResultType<V> type) {
        final var recipeResults = getAllResults();
        final var result = Sets.<SpellRecipeResult<V>>newHashSet();

        for (final var recipeResult : recipeResults) {
            if (recipeResult.type().equals(type)) {
                result.add((SpellRecipeResult<V>) recipeResult);
            }
        }

        return result;
    }

    double getCost();
}
