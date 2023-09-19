package mikolmisol.spellcraft.spells.modifiers;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.modifiers.impl.*;
import net.minecraft.core.Registry;

@UtilityClass
public class SpellcraftModifiers {
    public final Modifier POWERFUL = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Powerful.IDENTIFIER,
            new Powerful());

    public final Modifier DURATION = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Durable.IDENTIFIER,
            new Durable());

    public final Modifier DISTANT = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Distant.IDENTIFIER,
            new Distant());

    public final Modifier DIFFUSIVE = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Diffuse.IDENTIFIER,
            new Diffuse());

    public final Modifier SOFT = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Soft.IDENTIFIER,
            new Soft());

    public final Modifier ETHEREAL = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Ethereal.IDENTIFIER,
            new Ethereal());

    public final Modifier SEROUS = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Serous.IDENTIFIER,
            new Serous());

    public final Modifier MASSLESS = Registry.register(
            SpellcraftRegistries.MODIFIER,
            Massless.IDENTIFIER,
            new Massless());

    /*
    Must be invoked in the Spellcraft class to force-initialise this class.
    Necessary for SpellcraftRegistries to immediately contain the registered modifiers.
     */
    public static void initialise() {
    }
}
