package mikolmisol.spellcraft.data.languages;

import lombok.experimental.UtilityClass;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

@UtilityClass
public class SpellcraftLanguages {

    public void generate(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpellcraftEnglishLanguageProvider::new);
    }
}
