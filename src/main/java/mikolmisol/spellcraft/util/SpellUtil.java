package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.shapes.ShapeNode;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@UtilityClass
public class SpellUtil {

    private final Random RANDOM = new Random();
    private final String[] ANCIENT_WIZARD_NAMES = {
            "Mikolmisol",
            "Kimera",
            "Zamelar",
            "Victor",
            "Ezhemiel",
            "Kipuna",
            "Sheeana",
            "Odrade",
            "Inneia",
            "Samael",
            "Xezia",
            "Amaea",
            "Ultimar",
            "Zevier",
            "Uriah",
            "Hwi",
            "Ixina",
            "Myel",
            "Zittar"
    };

    public @NotNull Component getDefaultNameForSpell(@NotNull Spell spell) {
        final var shape = spell.getShape();
        final var effects = shape.getEffects();
        final var modifiers = shape.getModifiers();

        final var name = MutableComponent.create(ComponentContents.EMPTY);

        if (RANDOM.nextBoolean()) {
            name.append(ANCIENT_WIZARD_NAMES[RANDOM.nextInt(0, ANCIENT_WIZARD_NAMES.length)] + "'s ");
        }

        if (modifiers.size() > 0) {
            final var randomModifier = modifiers.get(RANDOM.nextInt(0, modifiers.size()));
            name.append(randomModifier.getName());
            name.append(" ");
        }

        final var shapeName = shape.getShape().getName();
        name.append(shapeName);
        name.append(" of ");

        final var randomEffect = effects.get(RANDOM.nextInt(0, effects.size()));
        final var randomEffectName = randomEffect.getEffect().getName();
        name.append(randomEffectName);

        return name;
    }
}
