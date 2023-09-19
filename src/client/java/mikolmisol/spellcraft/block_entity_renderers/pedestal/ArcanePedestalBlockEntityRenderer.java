package mikolmisol.spellcraft.block_entity_renderers.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mikolmisol.spellcraft.block_entities.pedestal.ArcanePedestalBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public final class ArcanePedestalBlockEntityRenderer implements BlockEntityRenderer<ArcanePedestalBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ArcanePedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ArcanePedestalBlockEntity pedestal, float tickDelta, PoseStack matrices, MultiBufferSource source, int light, int overlay) {
        light = LevelRenderer.getLightColor(pedestal.getLevel(), pedestal.getBlockPos().above());
        final var item = pedestal.getItem(0);
        final var model = itemRenderer.getItemModelShaper().getItemModel(item);

        matrices.pushPose();

        matrices.translate(0.5, 1.1, 0.5);

        matrices.mulPose(Axis.YP.rotation(Util.getMillis() / 1000f));
        matrices.translate(0, Mth.sin(1 + Util.getMillis() / 500f) / 20f, 0);

        itemRenderer.render(
            item,
            ItemDisplayContext.GROUND,
            false,
            matrices,
            source,
            light,
            OverlayTexture.NO_OVERLAY,
            model
        );

        matrices.popPose();
    }
}