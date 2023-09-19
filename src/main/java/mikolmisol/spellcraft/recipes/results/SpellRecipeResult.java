package mikolmisol.spellcraft.recipes.results;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.registries.SpellcraftRegistryResourceKeys;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SpellRecipeResult<V>(
        @NotNull V value,
        SpellRecipeResultType<V> type
) {

    public static <V> SpellRecipeResult<V> fromJson(ResourceLocation identifier, JsonObject object) {
        if (!object.has("type")) {
            throw new JsonSyntaxException("Expected 'type' string when parsing a result of the results array of recipe '" + identifier + "'.");
        }

        final var typeString = object.get("type").getAsString();

        try {
            final var type = (SpellRecipeResultType<V>) SpellcraftRegistries.SPELL_RECIPE_RESULT_TYPE.get(new ResourceLocation(typeString));

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
