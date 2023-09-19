package mikolmisol.spellcraft.recipes.results;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import mikolmisol.spellcraft.recipes.results.serializers.BlockSpellRecipeResultSerializer;
import mikolmisol.spellcraft.recipes.results.serializers.ItemStackSpellRecipeResultSerializer;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
@SuppressWarnings("unchecked")
public class SpellcraftSpellRecipeResultTypes {
    public final SpellRecipeResultType<ItemStack> ITEM =
            register(new SpellRecipeResultType<>(new ResourceLocation(MOD_ID, "item"), new ItemStackSpellRecipeResultSerializer()));

    public final SpellRecipeResultType<Block> BLOCK =
            register(new SpellRecipeResultType<>(new ResourceLocation(MOD_ID, "block"), new BlockSpellRecipeResultSerializer()));

    private <V> SpellRecipeResultType<V> register(SpellRecipeResultType<V> resultType) {
        return Registry.register(SpellcraftRegistries.SPELL_RECIPE_RESULT_TYPE, resultType.identifier(), resultType);
    }

    public void initialise() {
    }
}
