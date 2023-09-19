package mikolmisol.spellcraft.render_layers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.entities.impl.SkeletonMage;
import mikolmisol.spellcraft.models.SkeletonMageModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

@Environment(EnvType.CLIENT)
public final class SkeletonMagePossessionLayer extends RenderLayer<SkeletonMage, SkeletonMageModel> {
    public SkeletonMagePossessionLayer(RenderLayerParent<SkeletonMage, SkeletonMageModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int i, SkeletonMage entity, float f, float g, float h, float j, float k, float l) {

    }
}
