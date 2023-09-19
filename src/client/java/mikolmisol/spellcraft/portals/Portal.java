package mikolmisol.spellcraft.portals;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * In {@link net.minecraft.client.renderer.LevelRenderer#renderLevel(PoseStack, float, long, boolean, Camera, GameRenderer, LightTexture, Matrix4f)},
 * after rendering all solid stuff, check if any Portal intersects with the frustum.
 *
 * If yes, move and rotate the camera to the portal's destination, and create a new frustum. Check if any Portal intersects with the
 * new Frustum. If yes, recurse until the limit of 3 recursions is reached.
 *
 * Invoke {@link net.minecraft.client.renderer.LevelRenderer#renderLevel(PoseStack, float, long, boolean, Camera, GameRenderer, LightTexture, Matrix4f)}
 * on the portal's destination with the new frustum and set the render target to the portal's renderbuffer.
 *
 * Draw the renderbuffer on the portal and recurse if needed.
 */
public final class Portal {

    /**
     * For a 3x3 of 16x16 pixels.
     */
    private static final int
            WIDTH = 48,
            HEIGHT = 48;

    /**
     * No reason to go beyond 3 as the resolution is intentionally too low
     * for it to be visible.
     */
    private static final int MAXIMUM_RECURSION = 3;

    @Getter
    private @Nullable Portal destination;

    @Getter
    private final @NotNull Vec3 centrePosition;

    @Getter
    private final @NotNull AABB boundingBox;

    @Getter
    private final @NotNull Vector3f viewVector;

    @Getter
    private final @NotNull Vector3f leftVector;

    private int recursion;

    public Portal(@NotNull Vec3 centrePosition, @NotNull AABB boundingBox, @NotNull Vector3f viewVector, @NotNull Vector3f leftVector) {
        this.centrePosition = centrePosition;
        this.boundingBox = boundingBox;
        this.viewVector = viewVector;
        this.leftVector = leftVector;
    }

    public void render(
            @NotNull LevelRenderer levelRenderer,
            float tickDelta,
            long limitTime,
            @NotNull Camera camera,
            @NotNull GameRenderer gameRenderer,
            @NotNull LightTexture lightTexture,
            @NotNull Matrix4f projectionMatrix
    ) {
        recursion += 1;

        if (recursion > MAXIMUM_RECURSION) {
            return;
        }

        final var oldCameraPosition= camera.position;
        final var oldCameraForwards= camera.forwards;
        final var oldCameraLeft= camera.left;

        camera.position = centrePosition;
        camera.forwards = viewVector;
        camera.left     = leftVector;

        final var minecraft = Minecraft.getInstance();
        final var window = minecraft.getWindow();
        final var mainTarget = minecraft.getMainRenderTarget();

        final var oldWidth = window.getWidth();
        final var oldHeight = window.getHeight();

        window.setWidth(WIDTH);
        window.setHeight(HEIGHT);

        camera.setup(minecraft.level, minecraft.cameraEntity == null? minecraft.player : minecraft.cameraEntity, false, false, tickDelta);

        final var levelPoseStack = new PoseStack();

        final var target = new TextureTarget(
                WIDTH,
                HEIGHT,
                true, // depth?
                Minecraft.ON_OSX
        );

        target.bindWrite(true); // viewport?

        levelRenderer.graphicsChanged();

        levelRenderer.prepareCullFrustum(
                levelPoseStack,
                camera.getPosition(),
                projectionMatrix
        );

        levelRenderer.renderLevel(
                levelPoseStack,
                tickDelta,
                limitTime,
                false, // render block outline?
                camera,
                gameRenderer,
                lightTexture,
                projectionMatrix
        );

        target.unbindWrite();

        try (final var pixels = new NativeImage(WIDTH, HEIGHT, true)) {
            target.bindRead();
            pixels.downloadTexture(0, true);
            target.unbindRead();

            window.setWidth(oldWidth);
            window.setHeight(oldHeight);
            target.destroyBuffers();
        }

        camera.position = oldCameraPosition;
        camera.forwards = oldCameraForwards;
        camera.left = oldCameraLeft;

        levelRenderer.graphicsChanged();
        mainTarget.bindWrite(true);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Portal portal)) {
            return false;
        }

        return centrePosition.equals(portal.centrePosition);
    }
}
