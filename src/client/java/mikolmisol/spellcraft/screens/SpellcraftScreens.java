package mikolmisol.spellcraft.screens;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.menus.SpellcraftMenuTypes;
import mikolmisol.spellcraft.menus.arcane_core.ArcaneCoreMenu;
import net.minecraft.client.gui.screens.MenuScreens;

@UtilityClass
public class SpellcraftScreens {

    public void initialise() {
        MenuScreens.register(SpellcraftMenuTypes.ARCANE_WORKBENCH, ArcaneWorkbenchScreen::new);
        MenuScreens.register(SpellcraftMenuTypes.SPELL_CRAFTING_TABLE, SpellcraftingTableScreen::new);
        MenuScreens.register(SpellcraftMenuTypes.ARCANE_CORE, ArcaneCoreScreen::new);
    }
}
