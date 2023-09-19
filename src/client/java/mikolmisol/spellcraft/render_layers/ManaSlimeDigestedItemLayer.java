package mikolmisol.spellcraft.render_layers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.entities.impl.ManaSlime;
import mikolmisol.spellcraft.models.ManaSlimeModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;

@Environment(EnvType.CLIENT)
public final class ManaSlimeDigestedItemLayer extends RenderLayer<ManaSlime, ManaSlimeModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "mana_slime"), "digested_item");
    private final ItemRenderer itemRenderer;

    public ManaSlimeDigestedItemLayer(RenderLayerParent<ManaSlime, ManaSlimeModel> parentLayer, ItemRenderer itemRenderer) {
        super(parentLayer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int i, ManaSlime slime, float f, float g, float h, float j, float k, float l) {
        matrices.pushPose();

        final var item = slime.getItemBySlot(EquipmentSlot.MAINHAND);
        final var model = itemRenderer.getModel(item, slime.level, slime, slime.getId() + ItemDisplayContext.NONE.ordinal());
        itemRenderer.render(item, ItemDisplayContext.NONE, false, matrices, buffers, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model);

        matrices.popPose();
    }
}
