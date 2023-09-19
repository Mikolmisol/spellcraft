package mikolmisol.spellcraft.recipes.results.serializers;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultSerializer;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class ItemStackSpellRecipeResultSerializer implements SpellRecipeResultSerializer<ItemStack, SpellRecipeResult<ItemStack>> {
    @Override
    public SpellRecipeResult<ItemStack> fromJson(SpellRecipeResultType<ItemStack> type, ResourceLocation identifier, JsonObject object) {
        if (!object.has("item")) {
            throw new JsonSyntaxException("Expected 'item' string when parsing a result of recipe '" + identifier + "'.");
        }

        final var itemElement = object.get("item");

        if (!itemElement.isJsonPrimitive()) {
            throw new JsonSyntaxException("Expected 'item' string when parsing a result of recipe '" + identifier + "'.");
        }

        final var itemString = itemElement.getAsString();
        final var itemLocation = new ResourceLocation(itemString);
        final var item = BuiltInRegistries.BLOCK.get(itemLocation);

        var count = 1;
        if (object.has("count")) {
            count = object.get("count").getAsInt();

            if (count < 1) {
                throw new JsonSyntaxException("The count of a recipe result must be > 0 in recipe '" + identifier + "'.");
            }
        }

        return new SpellRecipeResult<>(new ItemStack(item, count), type);
    }

    @Override
    public SpellRecipeResult<ItemStack> fromNetwork(SpellRecipeResultType<ItemStack> type, ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(SpellRecipeResultType<ItemStack> type, FriendlyByteBuf buf, SpellRecipeResult<ItemStack> recipe) {

    }
}
