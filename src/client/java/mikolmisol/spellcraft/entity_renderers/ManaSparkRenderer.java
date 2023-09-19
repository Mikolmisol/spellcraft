package mikolmisol.spellcraft.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.entities.impl.ManaSpark;
import mikolmisol.spellcraft.util.ManaSparkRenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class ManaSparkRenderer extends EntityRenderer<ManaSpark> {

    public ManaSparkRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(ManaSpark entity, Frustum frustum, double d, double e, double f) {
        return true;
    }

    @Override
    public void render(ManaSpark entity, float tickDelta, float g, PoseStack matrices, MultiBufferSource buffers, int i) {
        matrices.pushPose();

        final var size = entity.getSize();

        ManaSparkRenderUtil.renderManaSpark(matrices, buffers, Vec3.ZERO, size, Util.getMillis() / 30f, tickDelta, 100);

        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ManaSpark entity) {
        return null;
    }
}
