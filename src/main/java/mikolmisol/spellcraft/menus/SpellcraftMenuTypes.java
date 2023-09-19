package mikolmisol.spellcraft.menus;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.menus.arcane_core.ArcaneCoreMenu;
import mikolmisol.spellcraft.menus.arcane_workbench.ArcaneWorkbenchMenu;
import mikolmisol.spellcraft.menus.spell_crafting_table.SpellCraftingTableMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
public class SpellcraftMenuTypes {

    public final MenuType<ArcaneWorkbenchMenu> ARCANE_WORKBENCH;

    public final MenuType<SpellCraftingTableMenu> SPELL_CRAFTING_TABLE;

    public final MenuType<ArcaneCoreMenu> ARCANE_CORE;

    static {
        ARCANE_WORKBENCH = Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, "arcane_workbench"), new MenuType<>(ArcaneWorkbenchMenu::new, FeatureFlags.VANILLA_SET));

        SPELL_CRAFTING_TABLE = Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, "spell_crafting_table"), new MenuType<>(SpellCraftingTableMenu::new, FeatureFlags.VANILLA_SET));

        ARCANE_CORE = Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, "arcane_core"), new MenuType<>(ArcaneCoreMenu::new, FeatureFlags.VANILLA_SET));
    }

    public void initialise() {
    }
}
