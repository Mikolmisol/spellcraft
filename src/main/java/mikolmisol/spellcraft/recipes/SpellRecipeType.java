package mikolmisol.spellcraft.recipes;

import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public record SpellRecipeType<T extends SpellRecipe>(
        @NotNull String name
) implements RecipeType<T> {
}
