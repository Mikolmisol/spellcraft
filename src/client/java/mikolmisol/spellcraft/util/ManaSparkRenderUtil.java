package mikolmisol.spellcraft.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

@UtilityClass
public class ManaSparkRenderUtil {
    private final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);

    public void renderManaSpark(PoseStack matrices, MultiBufferSource buffers, Vec3 position, float sizeMultiplier, float timer, float tick, int rayCount) {
        matrices.pushPose();

        matrices.translate(position.x, position.y, position.z);

        matrices.scale(sizeMultiplier, sizeMultiplier, sizeMultiplier);

        final var l = (1f + tick) / 200.0F;
        final var m = Math.min(l > 0.8F ? (l - 0.8F) / 0.2F : 0.0F, 1.0F);
        final var randomSource = RandomSource.create(432L);
        final var vertices = buffers.getBuffer(RenderType.lightning());

        for (var ray = 0; ray != rayCount; ray += 1) {
            matrices.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360f * timer / 300f));
            matrices.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360f));
            matrices.mulPose(Axis.ZP.rotationDegrees(randomSource.nextFloat() * 360f));
            matrices.mulPose(Axis.XP.rotationDegrees(randomSource.nextFloat() * 360f));
            matrices.mulPose(Axis.YP.rotationDegrees(randomSource.nextFloat() * 360f));

            final var o = randomSource.nextFloat() * 20f + 5f + m * 10f;
            final var p = randomSource.nextFloat() * 2f + 1f + m * 2f;
            final var q = (int) (255f * (1f - m));

            final var lastPose = matrices.last().pose();

            vertex1(vertices, lastPose, q);
            vertex2(vertices, lastPose, o, p);
            vertex3(vertices, lastPose, o, p);
            vertex1(vertices, lastPose, q);
            vertex3(vertices, lastPose, o, p);
            vertex4(vertices, lastPose, o, p);
            vertex1(vertices, lastPose, q);
            vertex4(vertices, lastPose, o, p);
            vertex2(vertices, lastPose, o, p);
        }

        matrices.popPose();
    }

    private void vertex1(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i) {
        vertexConsumer.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color(255, 255, 255, i).endVertex();
    }

    private void vertex2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g) {
        vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * g, f, -0.5F * g).color(0, 30, 255, 0).endVertex();
    }

    private void vertex3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g) {
        vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * g, f, -0.5F * g).color(0, 30, 255, 0).endVertex();
    }

    private void vertex4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g) {
        vertexConsumer.vertex(matrix4f, 0.0F, f, 0.5F * g).color(0, 30, 255, 0).endVertex();
    }
}
