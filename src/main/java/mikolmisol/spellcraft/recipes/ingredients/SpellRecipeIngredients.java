package mikolmisol.spellcraft.recipes.ingredients;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SpellRecipeIngredients(@NotNull List<SpellRecipeIngredient<?>> ingredients) {

    @SuppressWarnings("unchecked")
    public <V> @NotNull List<SpellRecipeIngredient<V>> getAllOfType(SpellRecipeIngredientType<V> type) {
        final List<SpellRecipeIngredient<V>> result = Lists.newArrayList();

        for (final var ingredient : ingredients) {
            if (ingredient.type().equals(type)) {
                result.add((SpellRecipeIngredient<V>) ingredient);
            }
        }

        return result;
    }
}
