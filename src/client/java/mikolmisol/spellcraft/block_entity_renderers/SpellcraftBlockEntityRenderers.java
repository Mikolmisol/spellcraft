package mikolmisol.spellcraft.block_entity_renderers;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entity_renderers.font.ArcaneFontBlockEntityRenderer;
import mikolmisol.spellcraft.block_entity_renderers.keystone.ArcaneKeystoneBlockEntityRenderer;
import mikolmisol.spellcraft.block_entity_renderers.pedestal.ArcanePedestalBlockEntityRenderer;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public final class SpellcraftBlockEntityRenderers {

    private SpellcraftBlockEntityRenderers() {
    }

    public static void initialise() {
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.MANA_CRYSTAL, RenderType.translucentNoCrumbling());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.STONE_BRICK_ARCANE_PEDESTAL_BLOCK, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.STONE_BRICK_ARCANE_FONT_BLOCK, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.SPELL_CRAFTING_TABLE_BLOCK, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.FORCE_FIELD, RenderType.translucentNoCrumbling());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.ARCANE_CRAFTING_FOCUS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.ARCANE_RELAY, RenderType.cutout());

        BlockEntityRenderers.register(SpellcraftBlockEntityTypes.MANA_CRYSTAL_ORE, ManaCrystalOreBlockEntityRenderer::new);
        BlockEntityRenderers.register(SpellcraftBlockEntityTypes.MANA_CRYSTAL, ManaCrystalBlockEntityRenderer::new);
        BlockEntityRenderers.register(SpellcraftBlockEntityTypes.ARCANE_KEYSTONE, ArcaneKeystoneBlockEntityRenderer::new);
        BlockEntityRenderers.register(SpellcraftBlockEntityTypes.ARCANE_FONT, ArcaneFontBlockEntityRenderer::new);
        BlockEntityRenderers.register(SpellcraftBlockEntityTypes.ARCANE_PEDESTAL, ArcanePedestalBlockEntityRenderer::new);
    }
}
