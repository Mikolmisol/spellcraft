package mikolmisol.spellcraft.item_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.render_data_types.BipyramidalCrystal;
import mikolmisol.spellcraft.util.ManaCrystalRenderUtil;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

public class ManaShardItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final BipyramidalCrystal CRYSTAL;

    static {
        final var upperApex = new Vector3f(0.5f, 0.75f, 0.5f);

        final var planeCorner1 = new Vector3f(0.35f, 0.55f, 0.35f);

        final var planeCorner2 = new Vector3f(0.35f, 0.55f, 0.65f);

        final var planeCorner3 = new Vector3f(0.65f, 0.55f, 0.65f);

        final var planeCorner4 = new Vector3f(0.65f, 0.55f, 0.35f);

        final var lowerApex = new Vector3f(0.5f, 0.35f, 0.5f);

        CRYSTAL = new BipyramidalCrystal(upperApex, planeCorner1, planeCorner2, planeCorner3, planeCorner4, lowerApex);
    }

    @Override
    public void render(ItemStack item, ItemDisplayContext transform, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        final var lastPose = matrices.last().pose();

        matrices.pushPose();

        ManaCrystalRenderUtil.renderManaCrystal(lastPose, buffers, CRYSTAL);

        matrices.popPose();
    }
}
