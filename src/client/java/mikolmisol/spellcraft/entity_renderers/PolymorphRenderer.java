package mikolmisol.spellcraft.entity_renderers;

import mikolmisol.spellcraft.models.PolymorphModel;
import mikolmisol.spellcraft.render_layers.PolymorphFurLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public final class PolymorphRenderer<T extends LivingEntity> extends LivingEntityRenderer<T, PolymorphModel<T>> {
    private static final ResourceLocation SHEEP_LOCATION = new ResourceLocation("textures/entity/sheep/sheep.png");

    public PolymorphRenderer(EntityRendererProvider.Context context) {
        super(context, new PolymorphModel<>(context.bakeLayer(ModelLayers.SHEEP)), 0.7F);
        addLayer(new PolymorphFurLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return SHEEP_LOCATION;
    }
}
