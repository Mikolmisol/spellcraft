package mikolmisol.spellcraft.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mikolmisol.spellcraft.entities.impl.SpellProjectile;
import mikolmisol.spellcraft.models.SpellProjectileModel;
import mikolmisol.spellcraft.render_types.SpellcraftRenderTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static mikolmisol.spellcraft.entities.impl.SpellProjectile.MAXIMUM_CREATION_TIMER;
import static mikolmisol.spellcraft.entities.impl.SpellProjectile.MAXIMUM_DESTRUCTION_TIMER;

@Environment(EnvType.CLIENT)
public final class SpellProjectileRenderer extends EntityRenderer<SpellProjectile> {
    private final SpellProjectileModel model;

    public SpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new SpellProjectileModel(context.bakeLayer(ModelLayers.SHULKER_BULLET));
    }

    private void renderBody(PoseStack matrices, MultiBufferSource buffers, float textureDiffuseRed, float textureDiffuseGreen, float textureDiffuseBlue, float stepTextureOffset, float stepAlpha) {
        final var mainBuffer = buffers.getBuffer(SpellcraftRenderTypes.forSpellProjectiles());
        model.renderToBuffer(matrices, mainBuffer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepAlpha);
    }

    private void renderAura(PoseStack matrices, MultiBufferSource buffers, float textureDiffuseRed, float textureDiffuseGreen, float textureDiffuseBlue, float stepTextureOffset, float stepAlpha, float stepRadius) {
        final var vertices = buffers.getBuffer(SpellcraftRenderTypes.forSpellAura(SpellProjectileModel.TEXTURE, stepTextureOffset, stepTextureOffset));
        matrices.scale(0.65f + stepRadius, 0.65f + stepRadius, 0.65f + stepRadius);
        model.renderToBuffer(matrices, vertices, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepAlpha);
    }

    @Override
    public void render(SpellProjectile projectile, float f, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int light) {
        matrices.pushPose();

        final var interpolatedYRotation = Mth.rotLerp(tickDelta, projectile.yRotO, projectile.getYRot());
        final var interpolatedXRotation = Mth.lerp(tickDelta, projectile.xRotO, projectile.getXRot());
        final var partialTicks = (float) projectile.tickCount + tickDelta;

        matrices.mulPose(Axis.YP.rotationDegrees(Mth.sin(partialTicks * 0.1F) * 180.0F));
        matrices.mulPose(Axis.XP.rotationDegrees(Mth.cos(partialTicks * 0.1F) * 180.0F));
        matrices.mulPose(Axis.ZP.rotationDegrees(Mth.sin(partialTicks * 0.15F) * 360.0F));
        matrices.scale(-0.5F, -0.5F, 0.5F);

        final var color = (int) Mth.lerp(partialTicks, projectile.getDecimalColor() - 50, projectile.getDecimalColor() + 50);

        final var red = (color & 16711680) >> 16;
        final var green = (color & '\uff00') >> 8;
        final var blue = color & 255;

        final var textureDiffuseRed = red / 255f;
        final var textureDiffuseGreen = green / 255f;
        final var textureDiffuseBlue = blue / 255f;

        final var stepTextureOffset = (float) Math.sin(partialTicks * 0.1f);
        var radius = 1f;
        var alpha = 1f;

        model.setupAnim(projectile, 0.0F, 0.0F, 0.0F, interpolatedYRotation, interpolatedXRotation);

        final var destructionTimer = projectile.getDestructionTimer();
        if (destructionTimer > 0) {
            final var destructionProgress = ((float) destructionTimer) / (MAXIMUM_DESTRUCTION_TIMER - 1);

            radius = 1 / destructionProgress;
            alpha = destructionProgress;

            renderBody(matrices, buffers, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepTextureOffset, alpha);
            renderAura(matrices, buffers, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepTextureOffset, alpha, radius);
            matrices.popPose();
            return;
        }

        if (entityRenderDispatcher.camera.getEntity().distanceToSqr(projectile) < 7.5) {
            matrices.popPose();
            return;
        }

        final var creationTimer = projectile.getCreationTimer();
        if (creationTimer > 0) {
            final var creationProgress = ((float) creationTimer) / (MAXIMUM_CREATION_TIMER - 1);

            radius = creationProgress;
            alpha = 1 / creationProgress;

            renderBody(matrices, buffers, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepTextureOffset, alpha);
            renderAura(matrices, buffers, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepTextureOffset, alpha, radius);
            matrices.popPose();
            return;
        }

        renderBody(matrices, buffers, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepTextureOffset, alpha);
        renderAura(matrices, buffers, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue, stepTextureOffset, alpha, radius);

        matrices.popPose();

        super.render(projectile, f, tickDelta, matrices, buffers, light);
    }

    @Override
    protected int getBlockLightLevel(SpellProjectile entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(SpellProjectile entity) {
        return SpellProjectileModel.TEXTURE;
    }
}
