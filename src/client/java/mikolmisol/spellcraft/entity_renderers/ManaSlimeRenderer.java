package mikolmisol.spellcraft.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.entities.impl.ManaSlime;
import mikolmisol.spellcraft.models.ManaSlimeModel;
import mikolmisol.spellcraft.render_layers.ManaSlimeDigestedItemLayer;
import mikolmisol.spellcraft.render_layers.ManaSlimeOuterLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public final class ManaSlimeRenderer extends MobRenderer<ManaSlime, ManaSlimeModel> {
    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation("textures/entity/slime/slime.png");

    public ManaSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new ManaSlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25f);
        addLayer(new ManaSlimeDigestedItemLayer(this, context.getItemRenderer()));
        addLayer(new ManaSlimeOuterLayer(this, context.getModelSet()));
    }

    @Override
    protected void scale(ManaSlime slime, PoseStack matrices, float tick) {
        final var size = slime.getSize();
        matrices.scale(size, size, size);
    }

    @Override
    public void render(ManaSlime slime, float f, float g, PoseStack matrices, MultiBufferSource buffers, int i) {
        shadowRadius = 0.25f * slime.getSize();
        super.render(slime, f, g, matrices, buffers, i);
    }

    @Override
    public ResourceLocation getTextureLocation(ManaSlime entity) {
        return SLIME_LOCATION;
    }
}
