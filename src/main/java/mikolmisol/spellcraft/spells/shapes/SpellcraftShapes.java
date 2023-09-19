package mikolmisol.spellcraft.spells.shapes;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.shapes.impl.*;
import net.minecraft.core.Registry;

@UtilityClass
public final class SpellcraftShapes {
    public final Shape SELF = Registry.register(
            SpellcraftRegistries.SHAPE,
            Self.IDENTIFIER,
            new Self());
    public final Shape TOUCH = Registry.register(
            SpellcraftRegistries.SHAPE,
            Touch.IDENTIFIER,
            new Touch());
    public final Shape BOLT = Registry.register(
            SpellcraftRegistries.SHAPE,
            Bolt.IDENTIFIER,
            new Bolt());

    public final Shape PROJECTILE = Registry.register(
            SpellcraftRegistries.SHAPE,
            Projectile.IDENTIFIER,
            new Projectile());

    public final Shape NOVA = Registry.register(
            SpellcraftRegistries.SHAPE,
            Nova.IDENTIFIER,
            new Nova());

    /*
    Must be invoked in the Spellcraft class.
    Necessary for the SpellcraftRegistry to immediately contain the registered shapes.
     */
    public static void initialise() {
    }
}
