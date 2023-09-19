package mikolmisol.spellcraft.data;

import mikolmisol.spellcraft.data.languages.SpellcraftLanguages;
import mikolmisol.spellcraft.data.recipes.SpellcraftRecipes;
import mikolmisol.spellcraft.data.tags.SpellcraftTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class SpellcraftDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        final var pack = generator.createPack();

        SpellcraftTags.generate(pack);
        SpellcraftRecipes.generate(pack);
        SpellcraftLanguages.generate(pack);
    }
}
