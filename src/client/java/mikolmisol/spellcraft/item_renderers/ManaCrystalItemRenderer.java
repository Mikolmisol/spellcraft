package mikolmisol.spellcraft.item_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.render_data_types.PrismaticBipyramidalCrystal;
import mikolmisol.spellcraft.util.ManaCrystalRenderUtil;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

public final class ManaCrystalItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack item, ItemDisplayContext transform, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        final var lastPose = matrices.last().pose();

        matrices.pushPose();

        var upperApex = new Vector3f(0.5f, 0.8f, 0.5f);
        var planeCorner1 = new Vector3f(0.4f, 0.5f, 0.4f);
        var planeCorner2 = new Vector3f(0.4f, 0.5f, 0.6f);
        var planeCorner3 = new Vector3f(0.6f, 0.5f, 0.6f);
        var planeCorner4 = new Vector3f(0.6f, 0.5f, 0.4f);
        var lowerPlaneCorner1 = new Vector3f(0.45f, 0f, 0.45f);
        var lowerPlaneCorner2 = new Vector3f(0.45f, 0f, 0.55f);
        var lowerPlaneCorner3 = new Vector3f(0.55f, 0f, 0.55f);
        var lowerPlaneCorner4 = new Vector3f(0.55f, 0f, 0.45f);

        ManaCrystalRenderUtil.renderManaCrystalCluster(lastPose, buffers, new PrismaticBipyramidalCrystal(upperApex, planeCorner1, planeCorner2, planeCorner3, planeCorner4, lowerPlaneCorner1, lowerPlaneCorner2, lowerPlaneCorner3, lowerPlaneCorner4));

        matrices.popPose();
    }
}
