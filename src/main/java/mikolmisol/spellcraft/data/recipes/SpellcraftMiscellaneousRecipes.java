package mikolmisol.spellcraft.data.recipes;

import mikolmisol.spellcraft.items.SpellcraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.function.Consumer;

public final class SpellcraftMiscellaneousRecipes extends FabricRecipeProvider {
    public SpellcraftMiscellaneousRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SpellcraftItems.BATTER)
                .requires(Items.WHEAT, 3)
                .requires(Items.EGG, 2)
                .requires(Items.MILK_BUCKET)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SpellcraftItems.BATTER)
                .requires(SpellcraftItems.WHEAT_FLOUR, 1)
                .requires(Items.EGG, 2)
                .requires(Items.MILK_BUCKET)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SpellcraftItems.SWEET_BATTER)
                .requires(SpellcraftItems.BATTER)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(SpellcraftItems.BATTER), has(SpellcraftItems.BATTER))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SpellcraftItems.FISH_AND_CHIPS)
                .requires(SpellcraftItems.FRIED_FISH)
                .requires(Items.BAKED_POTATO)
                .unlockedBy(getHasName(SpellcraftItems.BATTER), has(SpellcraftItems.BATTER))
                .save(exporter);
        
        smeltingResultFromBase(exporter, SpellcraftItems.SWEET_BATTER, SpellcraftItems.WAFFLE);

        smeltingResultFromBase(exporter, SpellcraftItems.BATTERED_FISH, SpellcraftItems.FRIED_FISH);
    }

    @Override
    public String getName() {
        return "Spellcraft Miscellaneous Recipes";
    }
}
