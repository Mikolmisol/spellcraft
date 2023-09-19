package mikolmisol.spellcraft.parts;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.parts.brushes.Brush;
import mikolmisol.spellcraft.parts.brushes.impl.HayBrush;
import mikolmisol.spellcraft.parts.foci.Focus;
import mikolmisol.spellcraft.parts.foci.impl.DiamondFocus;
import mikolmisol.spellcraft.parts.knob.Knob;
import mikolmisol.spellcraft.parts.knob.impl.IronKnob;
import mikolmisol.spellcraft.parts.rods.Rod;
import mikolmisol.spellcraft.parts.rods.impl.WoodRod;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import net.minecraft.core.Registry;

@UtilityClass
public class SpellcraftParts {

    public void initialise() {
    }

    @UtilityClass
    public class Brushes {
        public final Brush HAY = Registry.register(
                SpellcraftRegistries.BRUSH,
                HayBrush.IDENTIFIER,
                new HayBrush()
        );
    }

    @UtilityClass
    public class Foci {
        public final Focus DIAMOND = Registry.register(
                SpellcraftRegistries.FOCUS,
                DiamondFocus.IDENTIFIER,
                new DiamondFocus()
        );
    }

    @UtilityClass
    public class Knobs {
        public final Knob IRON = Registry.register(
                SpellcraftRegistries.KNOB,
                IronKnob.IDENTIFIER,
                new IronKnob()
        );
    }

    @UtilityClass
    public class Rods {
        public final Rod WOOD = Registry.register(
                SpellcraftRegistries.ROD,
                WoodRod.IDENTIFIER,
                new WoodRod()
        );
    }
}
