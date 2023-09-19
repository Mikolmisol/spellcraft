package mikolmisol.spellcraft.block_entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.block_entities.crystal.ManaCrystalBlockEntity;
import mikolmisol.spellcraft.render_data_types.PrismaticBipyramidalCrystal;
import mikolmisol.spellcraft.util.ManaCrystalRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Vector3f;

import static mikolmisol.spellcraft.blocks.crystal.ManaCrystalBlock.FACING;

public final class ManaCrystalBlockEntityRenderer implements BlockEntityRenderer<ManaCrystalBlockEntity> {
    public ManaCrystalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    private static Vector3f scaledOffset(float x, float y, float z, float scale) {
        return new Vector3f(
                x * scale + 0.5f,
                y * scale + 0.5f * scale,
                z * scale + 0.5f
        );
    }

    @Override
    public void render(ManaCrystalBlockEntity crystal, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        final var direction = crystal.getBlockState().getValue(FACING);

        final var lastPose = matrices.last().pose();

        final var size = 1;

        matrices.pushPose();

        var upperApex = scaledOffset(0, 0.3f, 0, size);
        var middlePlaneA = scaledOffset(-0.15f, 0, -0.15f, size);
        var middlePlaneB = scaledOffset(-0.15f, 0, 0.15f, size);
        var middlePlaneC = scaledOffset(0.15f, 0, 0.15f, size);
        var middlePlaneD = scaledOffset(0.15f, 0, -0.15f, size);

        var lowerPlaneA = scaledOffset(-0.12f, -0.5f, -0.12f, size);
        var lowerPlaneB = scaledOffset(-0.12f, -0.5f, 0.12f, size);
        var lowerPlaneC = scaledOffset(0.12f, -0.5f, 0.12f, size);
        var lowerPlaneD = scaledOffset(0.12f, -0.5f, -0.12f, size);

        switch (direction) {
            case DOWN -> {


            }
            case UP -> {

                ManaCrystalRenderUtil.renderManaCrystalCluster(lastPose, buffers, new PrismaticBipyramidalCrystal(upperApex, middlePlaneA, middlePlaneB, middlePlaneC, middlePlaneD, lowerPlaneA, lowerPlaneB, lowerPlaneC, lowerPlaneD));
            }
            case NORTH -> {
            }
            case SOUTH -> {
            }
            case WEST -> {
            }
            case EAST -> {
            }
        }

        matrices.popPose();
    }
}

