package mikolmisol.spellcraft.recipes;

import mikolmisol.spellcraft.recipes.crafters.SpellCrafter;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredient;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResult;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@ApiStatus.Internal
public abstract class AbstractSpellRecipe implements SpellRecipe {
    final List<SpellRecipeIngredient<?>> ingredients;

    final Set<SpellRecipeResult<?>> results;

    private final double cost;

    public AbstractSpellRecipe(final @NotNull ResourceLocation identifier, final @NotNull List<SpellRecipeIngredient<?>> ingredients, final @NotNull Set<SpellRecipeResult<?>> results, double cost) {
        this.ingredients = ingredients;
        this.results = results;
        this.cost = cost;
    }

    @Override
    public @NotNull List<SpellRecipeIngredient<?>> getAllIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull Set<SpellRecipeResult<?>> getAllResults() {
        return results;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean matches(SpellCrafter container, Level level) {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack assemble(SpellCrafter container, RegistryAccess registryAccess) {
        return null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return SpellRecipe.super.getIngredients();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return null;
    }
}
