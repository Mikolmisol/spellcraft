package mikolmisol.spellcraft.recipes.ingredients;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.recipes.ingredients.serializers.BlockSpellRecipeIngredientSerializer;
import mikolmisol.spellcraft.recipes.ingredients.serializers.ItemStackSpellRecipeIngredientSerializer;
import mikolmisol.spellcraft.recipes.ingredients.serializers.LivingEntitySpellRecipeIngredientSerializer;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

@UtilityClass
@SuppressWarnings("unchecked")
public class SpellcraftSpellRecipeIngredientTypes {
    public final SpellRecipeIngredientType<ItemStack> ITEM =
            register(new SpellRecipeIngredientType<>(new ResourceLocation(Spellcraft.MOD_ID, "item"), new ItemStackSpellRecipeIngredientSerializer()));

    public final SpellRecipeIngredientType<Block> BLOCK =
            register(new SpellRecipeIngredientType<>(new ResourceLocation(Spellcraft.MOD_ID, "block"), new BlockSpellRecipeIngredientSerializer()));

    public final SpellRecipeIngredientType<LivingEntityIngredient<?>> ENTITY =
            register(new SpellRecipeIngredientType<>(new ResourceLocation(Spellcraft.MOD_ID, "entity"), new LivingEntitySpellRecipeIngredientSerializer()));

    private <T> SpellRecipeIngredientType<T> register(SpellRecipeIngredientType<T> ingredientType) {
        return Registry.register(SpellcraftRegistries.SPELL_RECIPE_INGREDIENT_TYPE, ingredientType.identifier(), ingredientType);
    }

    public void initialise() {
    }
}
