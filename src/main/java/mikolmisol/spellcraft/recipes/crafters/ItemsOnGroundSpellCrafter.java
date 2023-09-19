package mikolmisol.spellcraft.recipes.crafters;

import com.google.common.collect.Sets;
import mikolmisol.spellcraft.block_entities.container.SimpleImplementedContainer;
import mikolmisol.spellcraft.recipes.SpellRecipe;
import mikolmisol.spellcraft.recipes.ingredients.SpellcraftSpellRecipeIngredientTypes;
import mikolmisol.spellcraft.recipes.results.SpellcraftSpellRecipeResultTypes;
import mikolmisol.spellcraft.spells.Caster;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ItemsOnGroundSpellCrafter implements SpellCrafter, SimpleImplementedContainer {
    private final List<ItemEntity> items;

    public ItemsOnGroundSpellCrafter(List<ItemEntity> items) {
        this.items = items;
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return items.stream().map(ItemEntity::getItem).toList();
    }

    @Override
    public boolean canCraft(@NotNull SpellRecipe recipe) {
        final var ingredients = recipe.getAllIngredients();

        for (final var ingredient : ingredients) {
            if (!ingredient.type().equals(SpellcraftSpellRecipeIngredientTypes.ITEM)) {
                return false;
            }
        }

        final var results = recipe.getAllResults();

        for (final var result : results) {
            if (!result.type().equals(SpellcraftSpellRecipeResultTypes.ITEM)) {
                return false;
            }
        }

        final var itemStacks = items.stream().map(ItemEntity::getItem).toList();

        final var consumed = Sets.<Integer>newHashSet();
        for (final var ingredient : recipe.getAllIngredientsOfType(SpellcraftSpellRecipeIngredientTypes.ITEM)) {
            var found = false;

            for (var index = 0; index < itemStacks.size(); index += 1) {
                if (consumed.contains(index)) {
                    continue;
                }

                final var item = itemStacks.get(index);


            }

            if (!found) {
                return false;
            }
        }

        return false;
    }

    @Override
    public void craft(@NotNull SpellRecipe recipe, @NotNull Caster caster, @NotNull Level level) {

    }


    @Override
    public void setChanged() {

    }
}
