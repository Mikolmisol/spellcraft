package mikolmisol.spellcraft.entity_renderers;

import mikolmisol.spellcraft.entities.impl.SkeletonMage;
import mikolmisol.spellcraft.models.SkeletonMageModel;
import mikolmisol.spellcraft.render_layers.SpellcraftRenderLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public final class SkeletonMageRenderer extends HumanoidMobRenderer<SkeletonMage, SkeletonMageModel> {
    private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public SkeletonMageRenderer(EntityRendererProvider.Context context) {
        this(context, SpellcraftRenderLayers.SKELETON_MAGE_BODY, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
    }

    public SkeletonMageRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ModelLayerLocation modelLayerLocation2, ModelLayerLocation modelLayerLocation3) {
        super(context, new SkeletonMageModel(context.bakeLayer(modelLayerLocation)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new SkeletonMageModel(context.bakeLayer(modelLayerLocation2)), new SkeletonMageModel(context.bakeLayer(modelLayerLocation3)), context.getModelManager()));
    }

    @Override
    protected boolean isShaking(SkeletonMage mage) {
        return mage.isShaking();
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonMage entity) {
        return SKELETON_LOCATION;
    }
}
