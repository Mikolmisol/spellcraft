package mikolmisol.spellcraft.recipes.ingredients;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface SpellRecipeIngredientSerializer<V, T extends SpellRecipeIngredient<V>> {
    @NotNull SpellRecipeIngredient<V> fromJson(SpellRecipeIngredientType<V> type, ResourceLocation identifier, JsonObject object);

    @NotNull SpellRecipeIngredient<V> fromNetwork(SpellRecipeIngredientType<V> type, ResourceLocation identifier, FriendlyByteBuf buf);

    void toNetwork(SpellRecipeIngredientType<V> type, FriendlyByteBuf buf, T recipe);
}
