package mikolmisol.spellcraft.blocks;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.blocks.barrier.ArcaneBarrierBlock;
import mikolmisol.spellcraft.blocks.barrier.ArcaneForceField;
import mikolmisol.spellcraft.blocks.catalyst.ArcaneCatalystBlock;
import mikolmisol.spellcraft.blocks.coil.ArcaneCoilBlock;
import mikolmisol.spellcraft.blocks.core.ArcaneCoreBlock;
import mikolmisol.spellcraft.blocks.core.ArcaneFontBlock;
import mikolmisol.spellcraft.blocks.core.ArcanePedestalBlock;
import mikolmisol.spellcraft.blocks.crafting_table.ArcaneCraftingFocusBlock;
import mikolmisol.spellcraft.blocks.crafting_table.ArcaneWorkbenchBlock;
import mikolmisol.spellcraft.blocks.crystal.ManaCrystalBlock;
import mikolmisol.spellcraft.blocks.launcher.ArcaneLauncherBlock;
import mikolmisol.spellcraft.blocks.moonflower.MoonflowerBlock;
import mikolmisol.spellcraft.blocks.ore.ManaCrystalOreBlock;
import mikolmisol.spellcraft.blocks.portal.ArcaneKeystoneBlock;
import mikolmisol.spellcraft.blocks.projector.ArcaneProjectorBlock;
import mikolmisol.spellcraft.blocks.pulsar.ArcanePulsarBlock;
import mikolmisol.spellcraft.blocks.relay.ArcaneRelayBlock;
import mikolmisol.spellcraft.blocks.spawner.ArcaneSpawnerBlock;
import mikolmisol.spellcraft.items.SpellcraftItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Set;

@UtilityClass
public class SpellcraftBlocks {
    public final Block MANA_CRYSTAL;

    public final Block MANA_CRYSTAL_ORE;

    public final Block DEEPSLATE_MANA_CRYSTAL_ORE;

    public final Block STONE_BRICK_ARCANE_FONT_BLOCK;

    public final Block STONE_BRICK_ARCANE_PEDESTAL_BLOCK;

    public final Block SPELL_CRAFTING_TABLE_BLOCK;

    public final Block FORCE_FIELD;

    public final Block ARCANE_BARRIER;

    public final Block ARCANE_PROJECTOR;

    public final Block ARCANE_CANNON;

    public final Block ARCANE_COIL;

    public final Block ARCANE_LANTERN;

    public final Block ARCANE_BRAZIER;

    public final Block ARCANE_CRAFTING_FOCUS;

    public final Block ARCANE_RELAY;

    public final Block ARCANE_WORKBENCH;

    public final Block ARCANE_CORE;

    public final Block LUNARCUS;

    public final Block PERMAFROST;
    
    public final Block ARCANE_SPAWNER;
    
    public final Block ARCANE_KEYSTONE;

    @Getter(lazy = true)
    private final Set<Block> allBlocks = extractBlocks();
    
    static {
        MANA_CRYSTAL = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "mana_crystal"), new ManaCrystalBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

        MANA_CRYSTAL_ORE = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "mana_crystal_ore"), new ManaCrystalOreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));

        DEEPSLATE_MANA_CRYSTAL_ORE = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "deepslate_mana_crystal_ore"), new ManaCrystalOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)));

        STONE_BRICK_ARCANE_FONT_BLOCK = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "stone_brick_arcane_font"), new ArcaneFontBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).lightLevel(ArcaneFontBlock::getLightLevelForState)));

        STONE_BRICK_ARCANE_PEDESTAL_BLOCK = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "stone_brick_arcane_pedestal"), new ArcanePedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

        SPELL_CRAFTING_TABLE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "spell_crafting_table"), new SpellCraftingTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

        FORCE_FIELD = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_force_field"), new ArcaneForceField(FabricBlockSettings.create().mapColor(DyeColor.PURPLE).strength(-1, 3600000f).noLootTable().noOcclusion().lightLevel(ArcaneForceField::getLightLevelForState)));

        ARCANE_BARRIER = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_barrier"), new ArcaneBarrierBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_PROJECTOR = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_projector"), new ArcaneProjectorBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_CANNON = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_cannon"), new ArcaneLauncherBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_COIL = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_coil"), new ArcaneCoilBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_LANTERN = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_lantern"), new ArcanePulsarBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_BRAZIER = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_brazier"), new ArcaneCatalystBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_CRAFTING_FOCUS = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_crafting_focus"), new ArcaneCraftingFocusBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_RELAY = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_relay"), new ArcaneRelayBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        ARCANE_WORKBENCH = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_workbench"), new ArcaneWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

        ARCANE_CORE = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_core"), new ArcaneCoreBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

        LUNARCUS = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "lunarcus"), new MoonflowerBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP)));

        PERMAFROST = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "permafrost"), new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

        ARCANE_SPAWNER = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_spawner"), new ArcaneSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.SPAWNER)));

        ARCANE_KEYSTONE = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Spellcraft.MOD_ID, "arcane_keystone"), new ArcaneKeystoneBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    }

    public Set<Block> extractBlocks() {
        final var blocks = Sets.<Block>newHashSet();

        final var items = SpellcraftItems.class;
        final var fields = items.getFields();

        for (final var field : fields) {
            try {
                final var object = field.get(fields);

                if (object instanceof final Block block) {
                    blocks.add(block);
                }

            } catch (IllegalAccessException e) {

                throw new RuntimeException(e);
            }
        }

        return blocks;
    }

    public void initialise() {
    }
}
