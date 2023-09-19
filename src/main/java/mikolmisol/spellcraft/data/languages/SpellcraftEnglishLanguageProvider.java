package mikolmisol.spellcraft.data.languages;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import mikolmisol.spellcraft.items.SpellcraftItems;
import mikolmisol.spellcraft.items.forge_hammer.ForgeHammer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;
import java.util.Set;

public final class SpellcraftEnglishLanguageProvider extends FabricLanguageProvider {

    public static final Map<String, String> TRANSLATIONS = Maps.newHashMap();

    public SpellcraftEnglishLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    private static String translateIdentifier(ResourceLocation identifier) {
        final var path = identifier.getPath();
        final var words = path.split("_");

        for (var index = 0; index < words.length; index += 1) {
            final var word = words[index];

            if (PREPOSITIONS.contains(word)) {
                continue;
            }

            words[index] = StringUtils.capitalize(words[index]);
        }

        return String.join(" ", words);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        for (final var item : SpellcraftItems.getAllItems()) {
            final var identifier = BuiltInRegistries.ITEM.getKey(item);
            builder.add(item, translateIdentifier(identifier));
        }

        for (final var block : SpellcraftBlocks.getAllBlocks()) {
            final var identifier = BuiltInRegistries.BLOCK.getKey(block);
            builder.add(block, translateIdentifier(identifier));
        }

        TRANSLATIONS.forEach(builder::add);
    }

    private static final Set<String> PREPOSITIONS = Sets.newHashSet("of");
}
