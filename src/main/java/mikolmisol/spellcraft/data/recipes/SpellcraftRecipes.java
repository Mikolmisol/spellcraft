package mikolmisol.spellcraft.data.recipes;

import lombok.experimental.UtilityClass;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

@UtilityClass
public class SpellcraftRecipes {

    public void generate(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpellcraftCompactingRecipes::new);
        pack.addProvider(SpellcraftForgeHammerRecipes::new);
    }
}
