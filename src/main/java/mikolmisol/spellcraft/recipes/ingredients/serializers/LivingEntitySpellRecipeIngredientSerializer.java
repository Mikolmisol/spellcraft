package mikolmisol.spellcraft.recipes.ingredients.serializers;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientSerializer;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientType;
import mikolmisol.spellcraft.recipes.ingredients.LivingEntityIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unchecked")
public class LivingEntitySpellRecipeIngredientSerializer implements SpellRecipeIngredientSerializer<LivingEntityIngredient<?>, SpellRecipeIngredient<LivingEntityIngredient<?>>> {
    @Override
    public @NotNull SpellRecipeIngredient<LivingEntityIngredient<?>> fromJson(SpellRecipeIngredientType<LivingEntityIngredient<?>> type, ResourceLocation identifier, JsonObject object) {
        if (!object.has("variants")) {
            throw new JsonSyntaxException("Expected a 'variants' array of objects when parsing an ingredient");
        }

        final var variants = object.getAsJsonArray("variants");
        final List<LivingEntityIngredient<?>> entities = Lists.newArrayList();

        for (final var variant : variants) {
            if (!variant.isJsonObject()) {
                throw new JsonSyntaxException("Expected a 'variants' array of objects when parsing an ingredient");
            }

            final var variantObject = variant.getAsJsonObject();

            if (variantObject.has("entity")) {
                var health = 0f;

                if (variantObject.has("health")) {
                    if (variantObject.get("health").getAsInt() < 1) {
                        throw new JsonSyntaxException("The 'health' of an entity ingredient variant must be > 0.");
                    }
                    health = variantObject.getAsInt();
                }

                final var entityString = variantObject.get("entity").getAsString();
                final var entityLocation = new ResourceLocation(entityString);
                final var entity = BuiltInRegistries.ENTITY_TYPE.get(entityLocation);

                if (entities.contains(entity)) {
                    throw new JsonSyntaxException("Duplicit use of entity ingredient variant '" + entityString + "'.");
                }

                entities.add(new LivingEntityIngredient<>((EntityType<? extends LivingEntity>) entity, health));

            } else if (variantObject.has("tag")) {
                var health = 0f;

                if (variantObject.has("health")) {
                    if (variantObject.get("health").getAsInt() < 1) {
                        throw new JsonSyntaxException("The 'health' of an entity ingredient variant must be > 0.");
                    }
                    health = variantObject.getAsInt();
                }

                final var tagString = variantObject.get("tag").getAsString();
                final var tagLocation = new ResourceLocation(tagString);
                final var tag = TagKey.create(Registries.ENTITY_TYPE, tagLocation);
                final var registeredEntities = BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(tag);

                for (final var registeredEntity : registeredEntities) {

                    if (entities.contains(registeredEntity)) {
                        throw new JsonSyntaxException("Duplicit use of block ingredient variant '" + BuiltInRegistries.ENTITY_TYPE.getKey(registeredEntity.value()) + "'.");
                    }

                    entities.add(new LivingEntityIngredient<>((EntityType<? extends LivingEntity>) registeredEntity.value(), health));
                }
            } else {
                throw new JsonSyntaxException("Expected 'entity' or 'tag' when parsing the 'variant' array of an ingredient of recipe '" + identifier + "'.");
            }
        }

        return new SpellRecipeIngredient<>(entities, type);
    }

    @Override
    public @NotNull SpellRecipeIngredient<LivingEntityIngredient<?>> fromNetwork(SpellRecipeIngredientType<LivingEntityIngredient<?>> type, ResourceLocation identifier, FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public void toNetwork(SpellRecipeIngredientType<LivingEntityIngredient<?>> type, FriendlyByteBuf buf, SpellRecipeIngredient<LivingEntityIngredient<?>> recipe) {

    }
}
