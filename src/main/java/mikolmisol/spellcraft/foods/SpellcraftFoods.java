package mikolmisol.spellcraft.foods;

import lombok.experimental.UtilityClass;
import net.minecraft.world.food.FoodProperties;

@UtilityClass
public class SpellcraftFoods {
    public final FoodProperties WAFFLE =
            new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();

    public final FoodProperties ICE_CREAM_WAFFLE =
            new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();
    public final FoodProperties FRIED_FISH =
            new FoodProperties.Builder().nutrition(8).saturationMod(0.7f).build();
    public static final FoodProperties FISH_AND_CHIPS =
            new FoodProperties.Builder().nutrition(10).saturationMod(0.8f).build();
}
