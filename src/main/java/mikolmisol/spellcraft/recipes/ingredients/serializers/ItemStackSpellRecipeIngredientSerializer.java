package mikolmisol.spellcraft.recipes.ingredients.serializers;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientSerializer;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.StreamSupport;

public final class ItemStackSpellRecipeIngredientSerializer implements SpellRecipeIngredientSerializer<ItemStack, SpellRecipeIngredient<ItemStack>> {
    @Override
    public @NotNull SpellRecipeIngredient<ItemStack> fromJson(SpellRecipeIngredientType<ItemStack> type, ResourceLocation identifier, JsonObject object) {
        if (!object.has("variants")) {
            throw new JsonSyntaxException("Expected a 'variants' array of objects when parsing an ingredient");
        }

        final var variants = object.getAsJsonArray("variants");
        final List<ItemStack> items = Lists.newArrayList();

        for (final var variant : variants) {
            if (!variant.isJsonObject()) {
                throw new JsonSyntaxException("Expected a 'variants' array of objects when parsing an ingredient");
            }

            final var variantObject = variant.getAsJsonObject();

            if (variantObject.has("item")) {
                var count = 0;

                if (variantObject.has("count")) {
                    if (variantObject.get("count").getAsInt() < 1) {
                        throw new JsonSyntaxException("The 'count' of an ingredient variant must be > 0.");
                    }
                    count = variantObject.getAsInt();
                }

                final var itemString = variantObject.get("item").getAsString();
                final var itemLocation = new ResourceLocation(itemString);
                final var item = BuiltInRegistries.ITEM.get(itemLocation);

                final var itemStack = new ItemStack(item, count);

                if (items.contains(itemStack)) {
                    throw new JsonSyntaxException("Duplicate use of item ingredient variant '" + itemString + "'.");
                }

                items.add(itemStack);

            } else if (variantObject.has("tag")) {
                var count = 0;

                if (variantObject.has("count")) {
                    if (variantObject.get("count").getAsInt() < 1) {
                        throw new JsonSyntaxException("The 'count' of an ingredient variant must be > 0.");
                    }
                    count = variantObject.getAsInt();
                }

                final var tagString = variantObject.get("tag").getAsString();
                final var tagLocation = new ResourceLocation(tagString);
                final var tag = TagKey.create(Registries.ITEM, tagLocation);
                final var registeredItems = StreamSupport.stream(BuiltInRegistries.ITEM.spliterator(), false).toList();

                for (final var registeredItem : registeredItems) {
                    if (!registeredItem.builtInRegistryHolder().is(tag)) {
                        continue;
                    }

                    final var itemStack = new ItemStack(registeredItem, count);

                    if (!items.contains(itemStack)) {
                        items.add(itemStack);
                    }
                }

            } else {
                throw new JsonSyntaxException("Expected 'item' or 'tag' when parsing the 'variant' array of an ingredient of recipe '" + identifier + "'.");
            }
        }

        return new SpellRecipeIngredient<>(items, type);
    }

    @Override
    public @NotNull SpellRecipeIngredient<ItemStack> fromNetwork(SpellRecipeIngredientType<ItemStack> type, ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(SpellRecipeIngredientType<ItemStack> type, FriendlyByteBuf buf, SpellRecipeIngredient<ItemStack> recipe) {

    }
}
