package mikolmisol.spellcraft.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.entities.impl.SpellBolt;
import mikolmisol.spellcraft.util.BoltRenderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public final class SpellBoltRenderer extends EntityRenderer<SpellBolt> {
    public SpellBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(SpellBolt entity, Frustum frustum, double d, double e, double f) {
        return true;
    }

    @Override
    public void render(SpellBolt bolt, float f, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int i) {
        final var origin = bolt.getOrigin();
        final var end = bolt.getEnd();
        final var color = bolt.getColor();
        final var seed = bolt.getSeed();

        final var lastPose = new Matrix4f(matrices.last().pose());
        final var camera = Minecraft.getInstance().cameraEntity;

        if (camera != null) {
            lastPose.translate(camera.getEyePosition().toVector3f());
        }

        matrices.pushPose();

        if (camera != null) {
            matrices.translate(camera.getX(), camera.getY(), camera.getZ());
        }

        BoltRenderHelper.renderBolt(lastPose, origin, end, 0.5, 0.1, 0.1, color, seed);

        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SpellBolt entity) {
        return null;
    }
}
