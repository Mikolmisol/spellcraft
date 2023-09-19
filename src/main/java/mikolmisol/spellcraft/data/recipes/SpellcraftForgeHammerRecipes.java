package mikolmisol.spellcraft.data.recipes;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.items.SpellcraftItems;
import mikolmisol.spellcraft.metals.Metal;
import mikolmisol.spellcraft.metals.Variant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public final class SpellcraftForgeHammerRecipes extends FabricRecipeProvider {
    public SpellcraftForgeHammerRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        // Ingot to Plate
        for (final var metal : Metal.values()) {
            if (!metal.isWorkableByForgeHammer()) {
                continue;
            }

            final var plate = metal.getItem(Variant.PLATE);
            final var ingots = metal.getTagKeys(Variant.INGOT);

            for (final var ingot : ingots) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, plate)
                        .requires(ingot)
                        .requires(ingot)
                        .requires(SpellcraftItems.FORGE_HAMMER)
                        .unlockedBy(getHasName(ingot), has(ingot))
                        .save(
                                exporter,
                                new ResourceLocation(
                                        Spellcraft.MOD_ID,
                                        metal.getName()+"_plate_from_common"+ingot.location().getPath()
                                )
                        );
            }
        }
    }

    private static String getHasName(TagKey<Item> tagKey) {
        return "has_"+tagKey.location();
    }

    @Override
    public String getName() {
        return "Forge Hammer Recipes";
    }
}
