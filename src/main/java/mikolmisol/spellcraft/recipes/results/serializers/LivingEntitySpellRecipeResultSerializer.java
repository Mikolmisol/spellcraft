package mikolmisol.spellcraft.recipes.results.serializers;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultSerializer;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unchecked")
public class LivingEntitySpellRecipeResultSerializer implements SpellRecipeResultSerializer<EntityType<? extends LivingEntity>, SpellRecipeResult<EntityType<? extends LivingEntity>>> {
    @Override
    public SpellRecipeResult<EntityType<? extends LivingEntity>> fromJson(SpellRecipeResultType<EntityType<? extends LivingEntity>> type, ResourceLocation identifier, JsonObject object) {
        if (!object.has("entity")) {
            throw new JsonSyntaxException("Expected 'entity' string when parsing a result of recipe '" + identifier + "'.");
        }

        final var entityElement = object.get("entity");

        if (!entityElement.isJsonPrimitive()) {
            throw new JsonSyntaxException("Expected 'entity' string when parsing a result of recipe '" + identifier + "'.");
        }

        final var entityString = entityElement.getAsString();
        final var entityLocation = new ResourceLocation(entityString);
        final var entity = BuiltInRegistries.ENTITY_TYPE.get(entityLocation);

        return new SpellRecipeResult<>((EntityType<? extends LivingEntity>) entity, type);
    }

    @Override
    public SpellRecipeResult<EntityType<? extends LivingEntity>> fromNetwork(SpellRecipeResultType<EntityType<? extends LivingEntity>> type, ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(SpellRecipeResultType<EntityType<? extends LivingEntity>> type, FriendlyByteBuf buf, SpellRecipeResult<EntityType<? extends LivingEntity>> recipe) {

    }
}
