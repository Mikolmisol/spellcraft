package mikolmisol.spellcraft.data.tags;

import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public final class SpellcraftBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public SpellcraftBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(SpellcraftBlocks.ARCANE_PROJECTOR, SpellcraftBlocks.ARCANE_RELAY, SpellcraftBlocks.ARCANE_CRAFTING_FOCUS,
                    SpellcraftBlocks.ARCANE_BARRIER, SpellcraftBlocks.ARCANE_LANTERN, SpellcraftBlocks.ARCANE_CANNON,
                    SpellcraftBlocks.ARCANE_COIL, SpellcraftBlocks.ARCANE_BRAZIER, SpellcraftBlocks.ARCANE_SPAWNER,

                    SpellcraftBlocks.MANA_CRYSTAL, SpellcraftBlocks.MANA_CRYSTAL_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(SpellcraftBlocks.ARCANE_CORE,

                        SpellcraftBlocks.DEEPSLATE_MANA_CRYSTAL_ORE);

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(SpellcraftBlocks.ARCANE_PROJECTOR, SpellcraftBlocks.ARCANE_RELAY, SpellcraftBlocks.ARCANE_CRAFTING_FOCUS,
                        SpellcraftBlocks.ARCANE_BARRIER, SpellcraftBlocks.ARCANE_LANTERN, SpellcraftBlocks.ARCANE_CANNON,
                        SpellcraftBlocks.ARCANE_COIL, SpellcraftBlocks.ARCANE_BRAZIER, SpellcraftBlocks.ARCANE_SPAWNER,

                        SpellcraftBlocks.MANA_CRYSTAL, SpellcraftBlocks.MANA_CRYSTAL_ORE,

                        SpellcraftBlocks.DEEPSLATE_MANA_CRYSTAL_ORE);

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(SpellcraftBlocks.ARCANE_WORKBENCH, SpellcraftBlocks.SPELL_CRAFTING_TABLE_BLOCK);
    }
}
