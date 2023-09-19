package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.data.languages.SpellcraftEnglishLanguageProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@UtilityClass
public class TextUtil {
    public @NotNull MutableComponent translate(@NotNull String translationKey, @NotNull String englishTranslation) {
        SpellcraftEnglishLanguageProvider.TRANSLATIONS.put(translationKey, englishTranslation);
        return Component.translatable(translationKey);
    }

    public @NotNull MutableComponent translateAndEdit(@NotNull String translationKey, @NotNull String englishTranslation, @NotNull Consumer<MutableComponent> editor) {
        SpellcraftEnglishLanguageProvider.TRANSLATIONS.put(translationKey, englishTranslation);

        final var component = Component.translatable(translationKey);

        editor.accept(component);
        return component;
    }

    public @NotNull Component translateWithArguments(@NotNull String translationKey, @NotNull String englishTranslation, @NotNull Object... arguments) {
        SpellcraftEnglishLanguageProvider.TRANSLATIONS.put(translationKey, englishTranslation);
        return Component.translatable(translationKey, arguments);
    }
}
