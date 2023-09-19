package mikolmisol.spellcraft.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.render_data_types.BipyramidalCrystal;
import mikolmisol.spellcraft.render_data_types.PrismaticBipyramidalCrystal;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static mikolmisol.spellcraft.render_types.SpellcraftRenderTypes.*;
import static net.minecraft.util.FastColor.ABGR32.color;

@UtilityClass
public class ManaCrystalRenderUtil {

    public void renderManaCrystalCluster(Matrix4f lastPose, MultiBufferSource buffers, PrismaticBipyramidalCrystal mainPrismaticBipyramidalCrystal) {
        final var prismA = new PrismaticBipyramidalCrystal(
                new Vector3f(mainPrismaticBipyramidalCrystal.upperApex().x() - 0.2f, mainPrismaticBipyramidalCrystal.upperApex().y() - 0.2f, mainPrismaticBipyramidalCrystal.upperApex().z() + 0.2f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneA().x() - 0.1f, mainPrismaticBipyramidalCrystal.middlePlaneA().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneA().z() + 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneB().x(), mainPrismaticBipyramidalCrystal.middlePlaneB().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneB().z() + 0.1f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneC().x() - 0.25f, mainPrismaticBipyramidalCrystal.middlePlaneC().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneC().z() + 0.1f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneD().x() - 0.1f, mainPrismaticBipyramidalCrystal.middlePlaneD().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneD().z() + 0.1f),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneA().x(), mainPrismaticBipyramidalCrystal.lowerPlaneA().y(), mainPrismaticBipyramidalCrystal.lowerPlaneA().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneB().x(), mainPrismaticBipyramidalCrystal.lowerPlaneB().y(), mainPrismaticBipyramidalCrystal.lowerPlaneB().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneC().x(), mainPrismaticBipyramidalCrystal.lowerPlaneC().y(), mainPrismaticBipyramidalCrystal.lowerPlaneC().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneD().x(), mainPrismaticBipyramidalCrystal.lowerPlaneD().y(), mainPrismaticBipyramidalCrystal.lowerPlaneD().z())
        );

        renderManaPrism(lastPose, buffers, prismA);

        final var prismB = new PrismaticBipyramidalCrystal(
                new Vector3f(mainPrismaticBipyramidalCrystal.upperApex().x() + 0.2f, mainPrismaticBipyramidalCrystal.upperApex().y() - 0.2f, mainPrismaticBipyramidalCrystal.upperApex().z() + 0.2f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneA().x() + 0.25f, mainPrismaticBipyramidalCrystal.middlePlaneA().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneA().z() + 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneB().x() + 0.25f, mainPrismaticBipyramidalCrystal.middlePlaneB().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneB().z() + 0.1f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneC().x() + 0.1f, mainPrismaticBipyramidalCrystal.middlePlaneC().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneC().z() + 0.1f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneD().x() + 0.1f, mainPrismaticBipyramidalCrystal.middlePlaneD().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneD().z() + 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneA().x(), mainPrismaticBipyramidalCrystal.lowerPlaneA().y(), mainPrismaticBipyramidalCrystal.lowerPlaneA().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneB().x(), mainPrismaticBipyramidalCrystal.lowerPlaneB().y(), mainPrismaticBipyramidalCrystal.lowerPlaneB().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneC().x(), mainPrismaticBipyramidalCrystal.lowerPlaneC().y(), mainPrismaticBipyramidalCrystal.lowerPlaneC().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneD().x(), mainPrismaticBipyramidalCrystal.lowerPlaneD().y(), mainPrismaticBipyramidalCrystal.lowerPlaneD().z())
        );

        renderManaPrism(lastPose, buffers, prismB);

        final var prismC = new PrismaticBipyramidalCrystal(
                new Vector3f(mainPrismaticBipyramidalCrystal.upperApex().x() + 0.2f, mainPrismaticBipyramidalCrystal.upperApex().y() - 0.2f, mainPrismaticBipyramidalCrystal.upperApex().z() - 0.2f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneA().x() + 0.25f, mainPrismaticBipyramidalCrystal.middlePlaneA().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneA().z() - 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneB().x() + 0.25f, mainPrismaticBipyramidalCrystal.middlePlaneB().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneB().z() - 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneC().x() + 0.1f, mainPrismaticBipyramidalCrystal.middlePlaneC().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneC().z() - 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.middlePlaneD().x() + 0.1f, mainPrismaticBipyramidalCrystal.middlePlaneD().y() - 0.2f, mainPrismaticBipyramidalCrystal.middlePlaneD().z() - 0.25f),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneA().x(), mainPrismaticBipyramidalCrystal.lowerPlaneA().y(), mainPrismaticBipyramidalCrystal.lowerPlaneA().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneB().x(), mainPrismaticBipyramidalCrystal.lowerPlaneB().y(), mainPrismaticBipyramidalCrystal.lowerPlaneB().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneC().x(), mainPrismaticBipyramidalCrystal.lowerPlaneC().y(), mainPrismaticBipyramidalCrystal.lowerPlaneC().z()),
                new Vector3f(mainPrismaticBipyramidalCrystal.lowerPlaneD().x(), mainPrismaticBipyramidalCrystal.lowerPlaneD().y(), mainPrismaticBipyramidalCrystal.lowerPlaneD().z())
        );

        renderManaPrism(lastPose, buffers, prismC);

        renderManaPrism(lastPose, buffers, mainPrismaticBipyramidalCrystal);
    }

    public void renderManaPrism(Matrix4f lastPose, MultiBufferSource buffers, PrismaticBipyramidalCrystal prismaticBipyramidalCrystal) {
        renderManaPrism(lastPose, buffers, prismaticBipyramidalCrystal, GlowOption.GLOW_NONE);
    }

    public void renderManaPrism(Matrix4f lastPose, MultiBufferSource buffers, PrismaticBipyramidalCrystal prismaticBipyramidalCrystal, GlowOption option) {
        var upperApexColor = color(100, 50, 50, 255);
        var planeColor = color(255, 1, 100, 200);
        var lowerPlaneColor = upperApexColor;

        if (option == GlowOption.GLOW_UPPER_APEX) {
            upperApexColor = color(255, 255, 255, 255);
        } else if (option != GlowOption.GLOW_NONE) {
            throw new IllegalArgumentException("Invalid option for crystal drawing: " + option);
        }

        final var rainbowSquaresRenderType = forRainbowSquares();
        final var rainbowSquares = buffers.getBuffer(rainbowSquaresRenderType);

        final var red = color(1, 255, 1, 1);
        final var green = color(1, 1, 255, 1);
        final var blue = color(1, 1, 1, 255);

        quad(rainbowSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneA(), prismaticBipyramidalCrystal.middlePlaneB(), prismaticBipyramidalCrystal.lowerPlaneA(), prismaticBipyramidalCrystal.lowerPlaneB(), red, green, blue, red);
        quad(rainbowSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneB(), prismaticBipyramidalCrystal.middlePlaneC(), prismaticBipyramidalCrystal.lowerPlaneB(), prismaticBipyramidalCrystal.lowerPlaneC(), red, blue, green, red);
        quad(rainbowSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneC(), prismaticBipyramidalCrystal.middlePlaneD(), prismaticBipyramidalCrystal.lowerPlaneC(), prismaticBipyramidalCrystal.lowerPlaneD(), red, green, blue, red);
        quad(rainbowSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneD(), prismaticBipyramidalCrystal.middlePlaneA(), prismaticBipyramidalCrystal.lowerPlaneD(), prismaticBipyramidalCrystal.lowerPlaneA(), red, blue, green, red);

        final var manaCrystalSquaresRenderType = forManaCrystalSquares();
        final var crystalSquares = buffers.getBuffer(manaCrystalSquaresRenderType);

        quad(crystalSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneA(), prismaticBipyramidalCrystal.middlePlaneB(), prismaticBipyramidalCrystal.lowerPlaneA(), prismaticBipyramidalCrystal.lowerPlaneB(), planeColor, planeColor, lowerPlaneColor, lowerPlaneColor);
        quad(crystalSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneB(), prismaticBipyramidalCrystal.middlePlaneC(), prismaticBipyramidalCrystal.lowerPlaneB(), prismaticBipyramidalCrystal.lowerPlaneC(), planeColor, planeColor, lowerPlaneColor, lowerPlaneColor);
        quad(crystalSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneC(), prismaticBipyramidalCrystal.middlePlaneD(), prismaticBipyramidalCrystal.lowerPlaneC(), prismaticBipyramidalCrystal.lowerPlaneD(), planeColor, planeColor, lowerPlaneColor, lowerPlaneColor);
        quad(crystalSquares, lastPose, prismaticBipyramidalCrystal.middlePlaneD(), prismaticBipyramidalCrystal.middlePlaneA(), prismaticBipyramidalCrystal.lowerPlaneD(), prismaticBipyramidalCrystal.lowerPlaneA(), planeColor, planeColor, lowerPlaneColor, lowerPlaneColor);

        // floor:
        quad(crystalSquares, lastPose, prismaticBipyramidalCrystal.lowerPlaneA(), prismaticBipyramidalCrystal.lowerPlaneB(), prismaticBipyramidalCrystal.lowerPlaneD(), prismaticBipyramidalCrystal.lowerPlaneC(), lowerPlaneColor, lowerPlaneColor, lowerPlaneColor, lowerPlaneColor);

        final var manaCrystalTrianglesRenderType = forManaCrystalTriangles();
        final var crystalTriangles = buffers.getBuffer(manaCrystalTrianglesRenderType);

        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneA(), prismaticBipyramidalCrystal.middlePlaneB(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneB(), prismaticBipyramidalCrystal.middlePlaneA(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneB(), prismaticBipyramidalCrystal.middlePlaneC(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneC(), prismaticBipyramidalCrystal.middlePlaneB(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneC(), prismaticBipyramidalCrystal.middlePlaneD(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneD(), prismaticBipyramidalCrystal.middlePlaneC(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneD(), prismaticBipyramidalCrystal.middlePlaneA(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, prismaticBipyramidalCrystal.upperApex(), prismaticBipyramidalCrystal.middlePlaneA(), prismaticBipyramidalCrystal.middlePlaneD(), upperApexColor, planeColor, planeColor);
    }

    public void renderManaCrystal(Matrix4f lastPose, MultiBufferSource buffers, BipyramidalCrystal crystal) {
        renderManaCrystal(lastPose, buffers, crystal, GlowOption.GLOW_NONE, 1);
    }

    public void renderManaCrystal(Matrix4f lastPose, MultiBufferSource buffers, BipyramidalCrystal crystal, GlowOption option, float glowMultiplier) {
        final var rainbowTrianglesRenderType = forRainbowTriangles();
        final var rainbowTriangles = buffers.getBuffer(rainbowTrianglesRenderType);

        final var red = color(1, 255, 10, 10);
        final var green = color(1, 10, 255, 10);
        final var blue = color(1, 10, 10, 255);

        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneA(), crystal.middlePlaneB(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneB(), crystal.middlePlaneA(), blue, green, red);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneB(), crystal.middlePlaneC(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneC(), crystal.middlePlaneB(), blue, green, red);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneC(), crystal.middlePlaneD(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneD(), crystal.middlePlaneC(), blue, green, red);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneD(), crystal.middlePlaneA(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneA(), crystal.middlePlaneD(), blue, green, red);

        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneA(), crystal.middlePlaneB(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneB(), crystal.middlePlaneA(), blue, green, red);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneB(), crystal.middlePlaneC(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneC(), crystal.middlePlaneB(), blue, green, red);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneC(), crystal.middlePlaneD(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneD(), crystal.middlePlaneC(), blue, green, red);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneD(), crystal.middlePlaneA(), red, green, blue);
        triangle(rainbowTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneA(), crystal.middlePlaneD(), blue, green, red);

        final var crystalRenderType = forManaCrystalTriangles();
        final var crystalTriangles = buffers.getBuffer(crystalRenderType);

        var upperApexColor = color(150, 50, 50, 255);
        var planeColor = color(255, 1, 100, 200);
        var lowerApexColor = upperApexColor;

        switch (option) {
            case GLOW_UPPER_APEX -> {
                upperApexColor = color((int) (255 * glowMultiplier), (int) (255 * glowMultiplier), (int) (255 * glowMultiplier), 255);
            }
            case GLOW_LOWER_APEX -> {
                lowerApexColor = color((int) (255 * glowMultiplier), (int) (255 * glowMultiplier), (int) (255 * glowMultiplier), 255);
            }
            case GLOW_BOTH -> {
                upperApexColor = color((int) (255 * glowMultiplier), (int) (255 * glowMultiplier), (int) (255 * glowMultiplier), 255);
                lowerApexColor = color((int) (255 * glowMultiplier), (int) (255 * glowMultiplier), (int) (255 * glowMultiplier), 255);
            }
        }

        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneA(), crystal.middlePlaneB(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneB(), crystal.middlePlaneA(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneB(), crystal.middlePlaneC(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneC(), crystal.middlePlaneB(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneC(), crystal.middlePlaneD(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneD(), crystal.middlePlaneC(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneD(), crystal.middlePlaneA(), upperApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.upperApex(), crystal.middlePlaneA(), crystal.middlePlaneD(), upperApexColor, planeColor, planeColor);

        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneA(), crystal.middlePlaneB(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneB(), crystal.middlePlaneA(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneB(), crystal.middlePlaneC(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneC(), crystal.middlePlaneB(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneC(), crystal.middlePlaneD(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneD(), crystal.middlePlaneC(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneD(), crystal.middlePlaneA(), lowerApexColor, planeColor, planeColor);
        triangle(crystalTriangles, lastPose, crystal.lowerApex(), crystal.middlePlaneA(), crystal.middlePlaneD(), lowerApexColor, planeColor, planeColor);
    }

    public void renderManaCrystals(Matrix4f lastPose, MultiBufferSource buffers, BipyramidalCrystal[] crystals) {
        for (final var crystal : crystals) {
            renderManaCrystal(lastPose, buffers, crystal);
        }
    }

    public void triangle(VertexConsumer vertices, Matrix4f matrix, Vector3f apex, Vector3f planeCorner1, Vector3f planeCorner2, int color1, int color2, int color3) {
        vertex(vertices, matrix, apex.x(), apex.y(), apex.z(), color1);
        vertex(vertices, matrix, planeCorner1.x(), planeCorner1.y(), planeCorner1.z(), color2);
        vertex(vertices, matrix, planeCorner2.x(), planeCorner2.y(), planeCorner2.z(), color3);
    }

    private void quad(VertexConsumer vertices, Matrix4f matrix4f, Vector3f a, Vector3f b, Vector3f c, Vector3f d, int colorA, int colorB, int colorC, int colorD) {
        vertex(vertices, matrix4f, a.x(), a.y(), a.z(), colorA);
        vertex(vertices, matrix4f, c.x(), c.y(), c.z(), colorC);
        vertex(vertices, matrix4f, d.x(), d.y(), d.z(), colorD);
        vertex(vertices, matrix4f, b.x(), b.y(), b.z(), colorB);
    }

    private void vertex(VertexConsumer vertices, Matrix4f matrix, float x, float y, float z, int color) {
        vertices.vertex(matrix, x, y, z).color(color).endVertex();
    }

    public enum GlowOption {
        GLOW_UPPER_APEX,
        GLOW_LOWER_APEX,
        GLOW_BOTH,
        GLOW_NONE
    }
}
