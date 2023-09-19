package mikolmisol.spellcraft.render_layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mikolmisol.spellcraft.models.PolymorphFurModel;
import mikolmisol.spellcraft.models.PolymorphModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

@Environment(EnvType.CLIENT)
public final class PolymorphFurLayer<T extends LivingEntity> extends RenderLayer<T, PolymorphModel<T>> {
    private static final ResourceLocation SHEEP_FUR_LOCATION = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private final PolymorphFurModel<T> model;

    public PolymorphFurLayer(RenderLayerParent<T, PolymorphModel<T>> renderLayerParent, EntityModelSet models) {
        super(renderLayerParent);
        this.model = new PolymorphFurModel<>(models.bakeLayer(ModelLayers.SHEEP_FUR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        if (entity.isInvisible()) {
            final var minecraft = Minecraft.getInstance();

            if (minecraft.shouldEntityAppearGlowing(entity)) {
                getParentModel().copyPropertiesTo(model);
                model.prepareMobModel(entity, f, g, h);
                model.setupAnim(entity, f, g, j, k, l);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.outline(SHEEP_FUR_LOCATION));
                model.renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
            }

        } else {
            float s;
            float t;
            float u;

            if (entity.hasCustomName() && "jeb_".equals(entity.getName().getString())) {
                final var n = entity.tickCount / 25 + entity.getId();
                final var o = DyeColor.values().length;
                final var p = n % o;
                final var q = (n + 1) % o;
                final var r = ((float) (entity.tickCount % 25) + h) / 25.0F;
                final var fs = Sheep.getColorArray(DyeColor.byId(p));
                final var gs = Sheep.getColorArray(DyeColor.byId(q));
                s = fs[0] * (1.0F - r) + gs[0] * r;
                t = fs[1] * (1.0F - r) + gs[1] * r;
                u = fs[2] * (1.0F - r) + gs[2] * r;
            } else {
                final var textureDiffuseColors = Sheep.getColorArray(DyeColor.WHITE);
                s = textureDiffuseColors[0];
                t = textureDiffuseColors[1];
                u = textureDiffuseColors[2];
            }

            coloredCutoutModelCopyLayerRender(getParentModel(), model, SHEEP_FUR_LOCATION, poseStack, multiBufferSource, i, entity, f, g, j, k, l, h, s, t, u);
        }
    }
}
