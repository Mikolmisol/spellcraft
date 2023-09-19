package mikolmisol.spellcraft.render_layers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.accessors.ArcaneShieldAccessor;
import mikolmisol.spellcraft.render_types.SpellcraftRenderTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public final class ArcaneShieldLayer<E extends LivingEntity, M extends EntityModel<E>> extends RenderLayer<E, M> {

    public ArcaneShieldLayer(RenderLayerParent<E, M> renderLayerParent, EntityModelSet models) {
        super(renderLayerParent);
    }

    public static void register(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> renderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper helper, EntityRendererProvider.Context context) {
        helper.register(new ArcaneShieldLayer<>(renderer, context.getModelSet()));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, E livingEntity, float f, float g, float tickDelta, float j, float k, float l) {
        if (!(livingEntity instanceof ArcaneShieldAccessor arcaneShield)) {
            return;
        }

        final var arcaneShieldStrength = arcaneShield.spellcraft_getArcaneShieldStrength();
        final var maximumArcaneShieldStrength = arcaneShield.spellcraft_getMaximumArcaneShieldStrength();
        final var arcaneShieldStrengthFraction = arcaneShieldStrength / maximumArcaneShieldStrength;

        matrices.pushPose();

        final var partialTicks = livingEntity.tickCount + tickDelta;

        final var vertices = buffers.getBuffer(SpellcraftRenderTypes.forArcaneBarrierEntity(-partialTicks * 0.007f % 1f, -partialTicks * 0.007f % 1f));

        // Math.abs(Math.sin(partialTicks * 0.01f))
        getParentModel().renderToBuffer(matrices, vertices, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0.3f, 0, 1, (float) (arcaneShieldStrengthFraction));

        matrices.popPose();
    }
}
