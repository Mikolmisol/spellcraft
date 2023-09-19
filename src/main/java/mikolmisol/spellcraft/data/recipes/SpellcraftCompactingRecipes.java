package mikolmisol.spellcraft.data.recipes;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.metals.Metal;
import mikolmisol.spellcraft.metals.Variant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public final class SpellcraftCompactingRecipes extends FabricRecipeProvider {
    public SpellcraftCompactingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        // Nugget to Ingot
        for (final var metal : Metal.values()) {
            if (metal.isVanilla() && metal != Metal.COPPER) {
                continue;
            }

            final var ingot = metal.getItem(Variant.INGOT);
            final var nuggets = metal.getTagKeys(Variant.NUGGET);

            for (final var nugget : nuggets) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                        .define('N', nugget)
                        .pattern("NNN")
                        .pattern("NNN")
                        .pattern("NNN")
                        .unlockedBy(getHasName(nugget), has(nugget))
                        .save(
                                exporter,
                                new ResourceLocation(
                                        Spellcraft.MOD_ID,
                                        metal.getName()+"_ingot_from_common_"+nugget.location().getPath()
                                )
                        );
            }
        }

        // Ingot to Nugget
        for (final var metal : Metal.values()) {
            if (metal.isVanilla() && metal != Metal.COPPER) {
                continue;
            }

            final var nugget = metal.getItem(Variant.NUGGET);
            final var ingots = metal.getTagKeys(Variant.INGOT);

            for (final var ingot : ingots) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                        .requires(ingot)
                        .unlockedBy(getHasName(ingot), has(ingot))
                        .save(
                                exporter,
                                new ResourceLocation(
                                        Spellcraft.MOD_ID,
                                        metal.getName()+"_nugget_from_common_"+ingot.location().getPath()
                                )
                        );
            }
        }

        // Ingot to Block
        for (final var metal : Metal.values()) {
            if (metal.isVanilla()) {
                continue;
            }

            final var block = metal.getItem(Variant.BLOCK);
            final var ingots = metal.getTagKeys(Variant.INGOT);

            for (final var ingot : ingots) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                        .define('I', ingot)
                        .pattern("III")
                        .pattern("III")
                        .pattern("III")
                        .unlockedBy(getHasName(ingot), has(ingot))
                        .save(
                                exporter,
                                new ResourceLocation(
                                        Spellcraft.MOD_ID,
                                        metal.getName() + "_block_from_common_" + ingot.location().getPath()
                                )
                        );
            }
        }

        // Block to Ingot
        for (final var metal : Metal.values()) {
            if (metal.isVanilla()) {
                continue;
            }

            final var blocks = metal.getTagKeys(Variant.BLOCK);
            final var ingot = metal.getItem(Variant.INGOT);

            for (final var block : blocks) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                        .requires(block)
                        .unlockedBy(getHasName(block), has(block))
                        .save(
                                exporter,
                                new ResourceLocation(
                                        Spellcraft.MOD_ID,
                                        metal.getName() + "_ingot_from_common_" + block.location().getPath()
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
        return "Compacting Recipes";
    }
}
