package mikolmisol.spellcraft.recipes.ingredients;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.registries.SpellcraftRegistryResourceKeys;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SpellRecipeIngredient<V>(
        @NotNull List<V> variants,
        @NotNull SpellRecipeIngredientType<V> type
) {
    public static <V> @NotNull SpellRecipeIngredient<V> fromJson(ResourceLocation identifier, JsonObject object) {
        if (!object.has("type")) {
            throw new JsonSyntaxException("Expected 'type' string when parsing an ingredient of the ingredients array of recipe '" + identifier + "'.");
        }

        final var typeString = object.get("type").getAsString();

        try {
            final var type = (SpellRecipeIngredientType<V>) SpellcraftRegistries.SPELL_RECIPE_INGREDIENT_TYPE.get(new ResourceLocation(typeString));

            return type.serializer().fromJson(type, identifier, object);

        } catch (NullPointerException nullPointerException) {
            throw new JsonSyntaxException(
                    String.format("Spell recipe result type '%s' was never registered in the '%s' registry.", typeString, SpellcraftRegistryResourceKeys.SPELL_RECIPE_RESULT_TYPE.location()),
                    nullPointerException
            );

        } catch (ResourceLocationException resourceLocationException) {
            throw new JsonSyntaxException(
                    String.format("'%s' is not a valid identifier. Format: 'namespace:path'.", typeString),
                    resourceLocationException
            );
        }
    }
}
