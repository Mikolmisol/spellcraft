package mikolmisol.spellcraft.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import lombok.experimental.UtilityClass;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Random;

@UtilityClass
public class BoltRenderHelper {

    public void renderBolt(Matrix4f pose, Vec3 origin, Vec3 end, double segmentLength, double thickness, double variance, int color, int seed) {
        final var tessellator = Tesselator.getInstance();
        final var buffer = tessellator.getBuilder();

        final var distance = origin.distanceTo(end);

        final var points = new Vec3[(int) (distance / segmentLength) + 1];
        points[0] = origin;
        points[points.length - 1] = end;

        final var random = new Random(seed);

        for (int i = 1; i < points.length - 1; i++) {
            final var x = origin.x + (end.x - origin.x) * ((double) i / (double) points.length) + (random.nextDouble() * 2 - 1) * variance;
            final var y = origin.y + (end.y - origin.y) * ((double) i / (double) points.length) + (random.nextDouble() * 2 - 1) * variance;
            final var z = origin.z + (end.z - origin.z) * ((double) i / (double) points.length) + (random.nextDouble() * 2 - 1) * variance;
            points[i] = new Vec3(x, y, z);
        }

        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        for (var i = 0; i < points.length - 1; i++) {
            final var segmentOrigin = points[i];
            final var segmentEnd = points[i + 1];

            final var forward = segmentEnd.subtract(segmentOrigin).normalize();
            final var right = new Vec3(forward.z, 0, -forward.x).normalize().scale(thickness);
            final var up = forward.cross(right).normalize().scale(thickness);

            final var topLeft = segmentOrigin.add(right).add(up);
            final var topRight = segmentOrigin.add(right).subtract(up);
            final var bottomLeft = segmentOrigin.subtract(right).add(up);
            final var bottomRight = segmentOrigin.subtract(right).subtract(up);

            buffer.vertex((float) topLeft.x, (float) topLeft.y, (float) topLeft.z).color(255, 255, 255, 255).endVertex();
            buffer.vertex((float) topRight.x, (float) topRight.y, (float) topRight.z).color(255, 255, 255, 255).endVertex();
            buffer.vertex((float) bottomLeft.x, (float) bottomLeft.y, (float) bottomLeft.z).color(255, 255, 255, 255).endVertex();

            buffer.vertex((float) topRight.x, (float) topRight.y, (float) topRight.z).color(255, 255, 255, 255).endVertex();
            buffer.vertex((float) bottomRight.x, (float) bottomRight.y, (float) bottomRight.z).color(255, 255, 255, 255).endVertex();
            buffer.vertex((float) bottomLeft.x, (float) bottomLeft.y, (float) bottomLeft.z).color(255, 255, 255, 255).endVertex();
        }

        tessellator.end();
    }
}
