package mikolmisol.spellcraft.render_layers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.entities.impl.ManaSlime;
import mikolmisol.spellcraft.models.ManaSlimeModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

@Environment(EnvType.CLIENT)
public final class ManaSlimeOuterLayer extends RenderLayer<ManaSlime, ManaSlimeModel> {
    private final ManaSlimeModel model;

    public ManaSlimeOuterLayer(RenderLayerParent<ManaSlime, ManaSlimeModel> parentLayer, EntityModelSet models) {
        super(parentLayer);
        this.model = new ManaSlimeModel(models.bakeLayer(ModelLayers.SLIME));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int i, ManaSlime slime, float f, float g, float h, float j, float k, float l) {
        final var vertices = buffers.getBuffer(RenderType.entityTranslucent(getTextureLocation(slime)));

        getParentModel().copyPropertiesTo(model);
        model.setupAnim(slime, f, g, j, k, l);
        model.renderToBuffer(matrices, vertices, i, LivingEntityRenderer.getOverlayCoords(slime, 0), 1, 1, 1, 1);
    }
}
