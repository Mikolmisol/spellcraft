package mikolmisol.spellcraft.block_entities;

import mikolmisol.spellcraft.block_entities.barrier.ArcaneBarrierBlockEntity;
import mikolmisol.spellcraft.block_entities.catalyst.ArcaneCatalystBlockEntity;
import mikolmisol.spellcraft.block_entities.coil.ArcaneCoilBlockEntity;
import mikolmisol.spellcraft.block_entities.core.ArcaneCoreBlockEntity;
import mikolmisol.spellcraft.block_entities.crafting_table.ArcaneCraftingFocusBlockEntity;
import mikolmisol.spellcraft.block_entities.crafting_table.ArcaneWorkbenchBlockEntity;
import mikolmisol.spellcraft.block_entities.crystal.ManaCrystalBlockEntity;
import mikolmisol.spellcraft.block_entities.font.ArcaneFontBlockEntity;
import mikolmisol.spellcraft.block_entities.launcher.ArcaneLauncherBlockEntity;
import mikolmisol.spellcraft.block_entities.ore.ManaCrystalOreBlockEntity;
import mikolmisol.spellcraft.block_entities.pedestal.ArcanePedestalBlockEntity;
import mikolmisol.spellcraft.block_entities.portal.ArcaneKeystoneBlockEntity;
import mikolmisol.spellcraft.block_entities.pulsar.ArcanePulsarBlockEntity;
import mikolmisol.spellcraft.block_entities.relay.ArcaneRelayBlockEntity;
import mikolmisol.spellcraft.block_entities.spawner.ArcaneSpawnerBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

public final class SpellcraftBlockEntityTypes {
    public static final BlockEntityType<ArcaneFontBlockEntity> ARCANE_FONT;

    public static final BlockEntityType<ArcanePedestalBlockEntity> ARCANE_PEDESTAL;

    public static final BlockEntityType<SpellcraftingTableBlockEntity> SPELL_CRAFTING_TABLE;

    public static final BlockEntityType<ArcaneBarrierBlockEntity> ARCANE_BARRIER;

    public static final BlockEntityType<ArcaneLauncherBlockEntity> ARCANE_CANNON;

    public static final BlockEntityType<ArcaneCoilBlockEntity> ARCANE_COIL;

    public static final BlockEntityType<ArcanePulsarBlockEntity> ARCANE_PULSAR;

    public static final BlockEntityType<ArcaneCatalystBlockEntity> ARCANE_BRAZIER;

    public static final BlockEntityType<ArcaneCraftingFocusBlockEntity> ARCANE_CRAFTING_FOCUS;

    public static final BlockEntityType<ManaCrystalBlockEntity> MANA_CRYSTAL;

    public static final BlockEntityType<ArcaneRelayBlockEntity> MANA_RELAY;

    public static final BlockEntityType<ManaCrystalOreBlockEntity> MANA_CRYSTAL_ORE;

    public static final BlockEntityType<ArcaneWorkbenchBlockEntity> ARCANE_WORKBENCH;

    public static final BlockEntityType<ArcaneCoreBlockEntity> ARCANE_CORE;
    public static final BlockEntityType<ArcaneSpawnerBlockEntity> ARCANE_SPAWNER;
    public static final BlockEntityType<ArcaneKeystoneBlockEntity> ARCANE_KEYSTONE;

    static {
        ARCANE_FONT = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "stone_brick_arcane_font"), FabricBlockEntityTypeBuilder.create(ArcaneFontBlockEntity::new, SpellcraftBlocks.STONE_BRICK_ARCANE_FONT_BLOCK).build());

        ARCANE_PEDESTAL = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "stone_brick_arcane_pedestal"), FabricBlockEntityTypeBuilder.create(ArcanePedestalBlockEntity::new, SpellcraftBlocks.STONE_BRICK_ARCANE_PEDESTAL_BLOCK).build());

        SPELL_CRAFTING_TABLE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "spell_crafting_table"), FabricBlockEntityTypeBuilder.create(SpellcraftingTableBlockEntity::new).addBlock(SpellcraftBlocks.SPELL_CRAFTING_TABLE_BLOCK).build());

        ARCANE_BARRIER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_barrier"), FabricBlockEntityTypeBuilder.create(ArcaneBarrierBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_BARRIER).build());

        ARCANE_CANNON = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_cannon"), FabricBlockEntityTypeBuilder.create(ArcaneLauncherBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_CANNON).build());

        ARCANE_COIL = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_coil"), FabricBlockEntityTypeBuilder.create(ArcaneCoilBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_COIL).build());

        ARCANE_PULSAR = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_lantern"), FabricBlockEntityTypeBuilder.create(ArcanePulsarBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_LANTERN).build());

        ARCANE_BRAZIER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_brazier"), FabricBlockEntityTypeBuilder.create(ArcaneCatalystBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_BRAZIER).build());

        ARCANE_CRAFTING_FOCUS = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_crafting_focus"), FabricBlockEntityTypeBuilder.create(ArcaneCraftingFocusBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_CRAFTING_FOCUS).build());

        MANA_CRYSTAL = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "mana_crystal_cluster"), FabricBlockEntityTypeBuilder.create(ManaCrystalBlockEntity::new).addBlock(SpellcraftBlocks.MANA_CRYSTAL).build());

        MANA_CRYSTAL_ORE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "mana_crystal_ore"), FabricBlockEntityTypeBuilder.create(ManaCrystalOreBlockEntity::new).addBlocks(SpellcraftBlocks.MANA_CRYSTAL_ORE, SpellcraftBlocks.DEEPSLATE_MANA_CRYSTAL_ORE).build());

        MANA_RELAY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "mana_relay"), FabricBlockEntityTypeBuilder.create(ArcaneRelayBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_RELAY).build());

        ARCANE_WORKBENCH = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_crafting_table"), FabricBlockEntityTypeBuilder.create(ArcaneWorkbenchBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_WORKBENCH).build());

        ARCANE_CORE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_core"), FabricBlockEntityTypeBuilder.create(ArcaneCoreBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_CORE).build());

        ARCANE_SPAWNER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_spawner"), FabricBlockEntityTypeBuilder.create(ArcaneSpawnerBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_SPAWNER).build());

        ARCANE_KEYSTONE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_keystone"), FabricBlockEntityTypeBuilder.create(ArcaneKeystoneBlockEntity::new).addBlock(SpellcraftBlocks.ARCANE_KEYSTONE).build());
    }

    public static void initialise() {
    }
}
