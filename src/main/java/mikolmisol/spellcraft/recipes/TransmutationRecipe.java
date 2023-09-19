package mikolmisol.spellcraft.recipes;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public final class TransmutationRecipe extends AbstractSpellRecipe {
    public static final SpellRecipeType<TransmutationRecipe> TYPE = new SpellRecipeType<>("transmutation");

    public static final SpellRecipeSerializer SERIALIZER = new SpellRecipeSerializer(TransmutationRecipe::new);

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "transmutation");

    public TransmutationRecipe(ResourceLocation identifier, @NotNull List<SpellRecipeIngredient<?>> ingredients, @NotNull Set<SpellRecipeResult<?>> results, double cost) {
        super(identifier, ingredients, results, cost);
    }

    @Override
    public ResourceLocation getId() {
        return IDENTIFIER;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }
}
