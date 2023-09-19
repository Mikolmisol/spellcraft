package mikolmisol.spellcraft.recipes.results;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface SpellRecipeResultSerializer<V, T extends SpellRecipeResult<V>> {
    T fromJson(SpellRecipeResultType<V> type, ResourceLocation identifier, JsonObject object);

    T fromNetwork(SpellRecipeResultType<V> type, ResourceLocation identifier, FriendlyByteBuf buf);

    void toNetwork(SpellRecipeResultType<V> type, FriendlyByteBuf buf, T recipe);
}
