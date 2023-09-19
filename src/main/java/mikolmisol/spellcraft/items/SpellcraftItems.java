package mikolmisol.spellcraft.items;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import mikolmisol.spellcraft.foods.SpellcraftFoods;
import mikolmisol.spellcraft.items.forge_hammer.ForgeHammer;
import mikolmisol.spellcraft.items.impl.ArcaneBroomItem;
import mikolmisol.spellcraft.items.impl.ManaCapacitor;
import mikolmisol.spellcraft.items.impl.ManaCrystalOreBlockItem;
import mikolmisol.spellcraft.metals.Metal;
import mikolmisol.spellcraft.metals.Variant;
import mikolmisol.spellcraft.util.TextUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

public final class SpellcraftItems {
    public static final Item MANA_CAPACITOR;

    public static final Item ENCHANTED_BROOM;

    public static final Item MANA_CRYSTAL_CLUSTER;

    public static final Item MANA_CRYSTAL_ORE;

    public static final Item DEEPSLATE_MANA_CRYSTAL_ORE;

    public static final Item MANA_SHARD;

    public static final Item MANA_GRIT;

    public static final Item STONE_BRICK_ARCANE_FONT;

    public static final Item STONE_BRICK_ARCANE_PEDESTAL;

    public static final Item ROASTED_MANA_BULB;

    public static final Item GROUND_MANA_BULB;

    public static final Item SPELL_CRAFTING_TABLE;

    public static final Item ARCANE_BARRIER;

    public static final Item ARCANE_PROJECTOR;

    public static final Item ARCANE_CANNON;

    public static final Item ARCANE_COIL;

    public static final Item ARCANE_LANTERN;

    public static final Item ARCANE_WORKBENCH;

    public static final Item ARCANE_CRAFTING_FOCUS;

    public static final Item ARCANE_RELAY;

    public static final Item MANA_BULB;
    
    public static final Item ARCANE_KEYSTONE;

    public static final Item ARCANE_CORE;

    public static final Item ARCANE_SPAWNER;

    public static final Item WAFFLE;

    public static final Item CHOCOLATE_ICE_CREAM_WAFFLE;

    public static final Item RED_BERRY_ICE_CREAM_WAFFLE;

    public static final Item GLOW_BERRY_ICE_CREAM_WAFFLE;

    public static final Item MELON_ICE_CREAM_WAFFLE;

    public static final Item CARAMEL_ICE_CREAM_WAFFLE;

    public static final Item FORGE_HAMMER;

    public static final Item GALENA;

    public static final Item BATTER;

    public static final Item SWEET_BATTER;

    public static final Item BATTERED_FISH;

    public static final Item FRIED_FISH;

    public static final Item FISH_AND_CHIPS;

    public static final Item WHEAT_FLOUR;

    public static final CreativeModeTab ITEMS;

    private static final Set<Item> ALL_ITEMS = Sets.newHashSet();

    private static @Nullable Set<CreativeModeTab> ALL_CREATIVE_MODE_TABS;

    static {
        MANA_CAPACITOR = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_capacitor"), new ManaCapacitor(new Item.Properties().stacksTo(1)));

        ENCHANTED_BROOM = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "enchanted_broom"), new ArcaneBroomItem(new Item.Properties().stacksTo(1)));

        MANA_CRYSTAL_CLUSTER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_crystal_cluster"), new BlockItem(SpellcraftBlocks.MANA_CRYSTAL, new Item.Properties().rarity(Rarity.RARE)));

        MANA_CRYSTAL_ORE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_crystal_ore"), new ManaCrystalOreBlockItem(SpellcraftBlocks.MANA_CRYSTAL_ORE, new Item.Properties().rarity(Rarity.RARE)));

        DEEPSLATE_MANA_CRYSTAL_ORE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "deepslate_mana_crystal_ore"), new ManaCrystalOreBlockItem(SpellcraftBlocks.DEEPSLATE_MANA_CRYSTAL_ORE, new Item.Properties().rarity(Rarity.RARE)));

        MANA_SHARD = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_shard"), new Item(new Item.Properties().rarity(Rarity.RARE)));

        MANA_GRIT = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_grit"), new Item(new Item.Properties().rarity(Rarity.RARE)));

        STONE_BRICK_ARCANE_FONT = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "stone_brick_arcane_font"), new BlockItem(SpellcraftBlocks.STONE_BRICK_ARCANE_FONT_BLOCK, new Item.Properties()));

        STONE_BRICK_ARCANE_PEDESTAL = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "stone_brick_arcane_pedestal"), new BlockItem(SpellcraftBlocks.STONE_BRICK_ARCANE_PEDESTAL_BLOCK, new Item.Properties()));

        ROASTED_MANA_BULB = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "roasted_cocoa_beans"), new Item(new Item.Properties()));

        GROUND_MANA_BULB = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "ground_cocoa_beans"), new Item(new Item.Properties()));

        SPELL_CRAFTING_TABLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "spell_crafting_table"), new BlockItem(SpellcraftBlocks.SPELL_CRAFTING_TABLE_BLOCK, new Item.Properties()));

        ARCANE_BARRIER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_barrier"), new BlockItem(SpellcraftBlocks.ARCANE_BARRIER, new Item.Properties()));

        ARCANE_PROJECTOR = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_projector"), new BlockItem(SpellcraftBlocks.ARCANE_PROJECTOR, new Item.Properties()));

        ARCANE_CANNON = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_cannon"), new BlockItem(SpellcraftBlocks.ARCANE_CANNON, new Item.Properties()));

        ARCANE_COIL = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_coil"), new BlockItem(SpellcraftBlocks.ARCANE_COIL, new Item.Properties()));

        ARCANE_LANTERN = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_lantern"), new BlockItem(SpellcraftBlocks.ARCANE_LANTERN, new Item.Properties()));

        ARCANE_WORKBENCH = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_workbench"), new BlockItem(SpellcraftBlocks.ARCANE_WORKBENCH, new Item.Properties()));

        ARCANE_CRAFTING_FOCUS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_crafting_focus"), new BlockItem(SpellcraftBlocks.ARCANE_CRAFTING_FOCUS, new Item.Properties()));

        ARCANE_RELAY = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_relay"), new BlockItem(SpellcraftBlocks.ARCANE_RELAY, new Item.Properties()));

        MANA_BULB = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "mana_bulb"), new ItemNameBlockItem(SpellcraftBlocks.LUNARCUS, new Item.Properties()));

        ARCANE_KEYSTONE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_keystone"), new BlockItem(SpellcraftBlocks.ARCANE_KEYSTONE, new Item.Properties()));

        ARCANE_CORE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_core"), new BlockItem(SpellcraftBlocks.ARCANE_CORE, new Item.Properties()));

        ARCANE_SPAWNER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "arcane_spawner"), new BlockItem(SpellcraftBlocks.ARCANE_SPAWNER, new Item.Properties()));

        WAFFLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "waffle"), new Item(new Item.Properties().food(SpellcraftFoods.WAFFLE)));

        CHOCOLATE_ICE_CREAM_WAFFLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "chocolate_ice_cream_waffle"), new Item(new Item.Properties().food(SpellcraftFoods.ICE_CREAM_WAFFLE)));

        RED_BERRY_ICE_CREAM_WAFFLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "red_berry_ice_cream_waffle"), new Item(new Item.Properties().food(SpellcraftFoods.ICE_CREAM_WAFFLE)));

        GLOW_BERRY_ICE_CREAM_WAFFLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "glow_berry_ice_cream_waffle"), new Item(new Item.Properties().food(SpellcraftFoods.ICE_CREAM_WAFFLE)));

        MELON_ICE_CREAM_WAFFLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "melon_ice_cream_waffle"), new Item(new Item.Properties().food(SpellcraftFoods.ICE_CREAM_WAFFLE)));

        CARAMEL_ICE_CREAM_WAFFLE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "caramel_ice_cream_waffle"), new Item(new Item.Properties().food(SpellcraftFoods.ICE_CREAM_WAFFLE)));

        FORGE_HAMMER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "forge_hammer"), new ForgeHammer(new Item.Properties().stacksTo(1)));

        GALENA = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "galena"), new Item(new Item.Properties()));

        BATTER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "batter"), new Item(new Item.Properties()));

        SWEET_BATTER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "sweet_batter"), new Item(new Item.Properties()));

        BATTERED_FISH = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "battered_fish"), new Item(new Item.Properties()));

        FRIED_FISH = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "fried_fish"), new Item(new Item.Properties().food(SpellcraftFoods.FRIED_FISH)));

        FISH_AND_CHIPS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "fish_and_chips"), new Item(new Item.Properties().food(SpellcraftFoods.FISH_AND_CHIPS)));

        WHEAT_FLOUR = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "battered_fish"), new Item(new Item.Properties()));

        final var metals = Lists.<Item>newArrayList();
        for (final var metal : Metal.values()) {
            for (final var variant : Variant.values()) {

                if (!metal.isAdded(variant)) {
                    continue;
                }

                final var identifier = metal.getIdentifier(variant);

                final var item = Registry.register(
                        BuiltInRegistries.ITEM,
                        identifier,
                        new Item(new Item.Properties())
                );

                metals.add(item);
            }
        }

        ALL_ITEMS.addAll(metals);

        ITEMS = Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                new ResourceLocation(MOD_ID, "items"),
                FabricItemGroup.builder()
                        .icon(MANA_SHARD::getDefaultInstance)
                        .title(TextUtil.translate(String.format("itemGroup.%s.items", MOD_ID), "Spellcraft Items"))
                        .displayItems((parameters, output) -> {

                            for (final var item : getAllItems()) {
                                output.accept(item);
                            }

                        }).build()
        );
    }

    private static boolean GENERATED = false;

    public static Set<Item> getAllItems() {
        if (GENERATED) {
            return ALL_ITEMS;
        }

        GENERATED = true;

        final var items = SpellcraftItems.class;
        final var fields = items.getFields();

        for (final var field : fields) {
            try {
                final var object = field.get(fields);

                if (object instanceof final Item item) {
                    ALL_ITEMS.add(item);
                }

            } catch (IllegalAccessException e) {

                throw new RuntimeException(e);
            }
        }

        return ALL_ITEMS;
    }

    public static void initialise() {
    }
}
