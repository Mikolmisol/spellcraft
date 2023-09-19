package mikolmisol.spellcraft.recipes.results.serializers;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultSerializer;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BlockSpellRecipeResultSerializer implements SpellRecipeResultSerializer<Block, SpellRecipeResult<Block>> {
    @Override
    public SpellRecipeResult<Block> fromJson(SpellRecipeResultType<Block> type, ResourceLocation identifier, JsonObject object) {
        if (!object.has("block")) {
            throw new JsonSyntaxException("Expected 'block' string when parsing a result of recipe '" + identifier + "'.");
        }

        final var blockElement = object.get("block");

        if (!blockElement.isJsonPrimitive()) {
            throw new JsonSyntaxException("Expected 'block' string when parsing a result of recipe '" + identifier + "'.");
        }

        final var blockString = blockElement.getAsString();
        final var blockLocation = new ResourceLocation(blockString);
        final var block = BuiltInRegistries.BLOCK.get(blockLocation);

        return new SpellRecipeResult<>(block, type);
    }

    @Override
    public SpellRecipeResult<Block> fromNetwork(SpellRecipeResultType<Block> type, ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(SpellRecipeResultType<Block> type, FriendlyByteBuf buf, SpellRecipeResult<Block> recipe) {

    }
}
