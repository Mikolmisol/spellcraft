package mikolmisol.spellcraft.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public final class ArcaneBroomRenderer extends EntityRenderer<ArcaneBroom> {
    private final EntityRendererProvider.Context context;

    public ArcaneBroomRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ResourceLocation getTextureLocation(ArcaneBroom entity) {
        return new ResourceLocation(Spellcraft.MOD_ID, "textures/entity/iron.png");
    }

    @Override
    public void render(ArcaneBroom entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int i) {
        matrices.pushPose();

        final var minecraft = Minecraft.getInstance();
        final var camera = minecraft.cameraEntity;

        matrices.mulPose(Axis.YP.rotationDegrees(180f - yaw));

        if (camera != null) {
            matrices.mulPose(Axis.XP.rotationDegrees(360f - Mth.clamp(Mth.lerp(tickDelta, camera.getXRot(), camera.xRotO), -20, 20)));
        }

        matrices.translate(0, 1.3, 0);

        // model.renderToBuffer(matrices, buffers.getBuffer(model.renderType(getTextureLocation(entity))), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        matrices.popPose();
    }
}
