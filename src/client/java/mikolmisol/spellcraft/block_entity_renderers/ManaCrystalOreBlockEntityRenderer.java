package mikolmisol.spellcraft.block_entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.block_entities.ore.ManaCrystalOreBlockEntity;
import mikolmisol.spellcraft.util.ManaCrystalRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public final class ManaCrystalOreBlockEntityRenderer implements BlockEntityRenderer<ManaCrystalOreBlockEntity> {

    public ManaCrystalOreBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ManaCrystalOreBlockEntity ore, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        final var lastPose = matrices.last().pose();

        matrices.pushPose();

        final var crystals = ore.getCrystals();

        ManaCrystalRenderUtil.renderManaCrystals(lastPose, buffers, crystals);

        matrices.popPose();
    }
}
