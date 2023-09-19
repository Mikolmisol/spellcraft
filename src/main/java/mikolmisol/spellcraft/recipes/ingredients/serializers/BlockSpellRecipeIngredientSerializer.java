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
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class BlockSpellRecipeIngredientSerializer implements SpellRecipeIngredientSerializer<Block, SpellRecipeIngredient<Block>> {
    @Override
    public @NotNull SpellRecipeIngredient<Block> fromJson(SpellRecipeIngredientType<Block> type, ResourceLocation identifier, JsonObject object) {
        if (!object.has("variants")) {
            throw new JsonSyntaxException("Expected a 'variants' array of objects when parsing an ingredient");
        }

        final var variants = object.getAsJsonArray("variants");
        final List<Block> blocks = Lists.newArrayList();

        for (final var variant : variants) {
            if (!variant.isJsonObject()) {
                throw new JsonSyntaxException("Expected a 'variants' array of objects when parsing an ingredient");
            }

            final var variantObject = variant.getAsJsonObject();

            if (variantObject.has("block")) {
                final var blockString = variantObject.get("block").getAsString();
                final var blockLocation = new ResourceLocation(blockString);
                final var block = BuiltInRegistries.BLOCK.get(blockLocation);

                if (blocks.contains(block)) {
                    throw new JsonSyntaxException("Duplicit use of item ingredient variant '" + blockString + "'.");
                }

                blocks.add(block);

            } else if (variantObject.has("tag")) {
                final var tagString = variantObject.get("tag").getAsString();
                final var tagLocation = new ResourceLocation(tagString);
                final var tag = TagKey.create(Registries.BLOCK, tagLocation);
                final var registeredBlocks = BuiltInRegistries.BLOCK.getTagOrEmpty(tag);

                for (final var registeredBlock : registeredBlocks) {

                    if (blocks.contains(registeredBlock)) {
                        throw new JsonSyntaxException("Duplicit use of block ingredient variant '" + BuiltInRegistries.BLOCK.getKey(registeredBlock.value()) + "'.");
                    }

                    blocks.add(registeredBlock.value());
                }
            } else {
                throw new JsonSyntaxException("Expected 'block' or 'tag' when parsing the 'variant' array of an ingredient of recipe '" + identifier + "'.");
            }
        }

        return new SpellRecipeIngredient<>(blocks, type);
    }

    @Override
    public @NotNull SpellRecipeIngredient<Block> fromNetwork(SpellRecipeIngredientType<Block> type, ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(SpellRecipeIngredientType<Block> type, FriendlyByteBuf buf, SpellRecipeIngredient<Block> recipe) {

    }
}
