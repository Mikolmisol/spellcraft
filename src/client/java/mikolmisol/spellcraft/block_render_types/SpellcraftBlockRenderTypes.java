package mikolmisol.spellcraft.block_render_types;

import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public final class SpellcraftBlockRenderTypes {

    private SpellcraftBlockRenderTypes() {
    }

    public static void initialise() {
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.MANA_CRYSTAL, RenderType.translucentNoCrumbling());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.STONE_BRICK_ARCANE_PEDESTAL_BLOCK, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.STONE_BRICK_ARCANE_FONT_BLOCK, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.SPELL_CRAFTING_TABLE_BLOCK, RenderType.solid());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.FORCE_FIELD, RenderType.translucentNoCrumbling());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.ARCANE_CRAFTING_FOCUS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SpellcraftBlocks.ARCANE_RELAY, RenderType.cutout());
    }
}
