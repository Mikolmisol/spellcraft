package mikolmisol.spellcraft.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public record SpellRecipeSerializer(
        @NotNull SpellRecipeConstructor constructor
) implements RecipeSerializer<SpellRecipe> {

    @Override
    public SpellRecipe fromJson(ResourceLocation identifier, JsonObject object) {
        final var recipe = new Gson().fromJson(object, SpellRecipeFormat.class);

        if (recipe.ingredients == null) {
            throw new JsonSyntaxException("Expected an 'ingredients' array of objects when parsing recipe '" + identifier + "'.");
        }

        if (recipe.results == null) {
            throw new JsonSyntaxException("Expected a 'result' array when parsing recipe '" + identifier + "'.");
        }

        if (recipe.cost < 0) {
            throw new JsonSyntaxException("Expected a number >= 0 as the cost of the recipe when parsing recipe '" + identifier + "'.");
        }

        final List<SpellRecipeIngredient<?>> ingredients = Lists.newArrayList();
        for (final var element : recipe.ingredients) {
            if (!element.isJsonObject()) {
                throw new JsonSyntaxException("Expected an 'ingredients' array of objects when parsing recipe '" + identifier + "'.");
            }

            ingredients.add(SpellRecipeIngredient.fromJson(identifier, element.getAsJsonObject()));
        }

        final Set<SpellRecipeResult<?>> results = Sets.newHashSet();
        for (final var element : recipe.results) {
            if (!element.isJsonObject()) {
                throw new JsonSyntaxException("Expected a 'results' array of objects when parsing recipe '" + identifier + "'.");
            }

            results.add(SpellRecipeResult.fromJson(identifier, element.getAsJsonObject()));
        }

        return constructor.construct(identifier, ingredients, results, recipe.cost);
    }

    @Override
    public SpellRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, SpellRecipe recipe) {

    }

    static class SpellRecipeFormat {
        public JsonArray ingredients;
        public JsonArray results;
        public double cost;
    }
}
