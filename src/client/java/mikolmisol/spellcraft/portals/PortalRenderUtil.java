package mikolmisol.spellcraft.portals;

import lombok.experimental.UtilityClass;
import mikolmisol.accessors.PortalsAccessor;
import mikolmisol.spellcraft.mixin.client.SpellcraftLevelRendererMixin;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Map;

@UtilityClass
public class PortalRenderUtil {
    public static void renderPortals(float tickDelta, long limitTime, Camera camera, LevelRenderer levelRenderer, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, Frustum frustum, Map<Vec3, Portal> portals) {
        portals.forEach((centrePosition, portal) -> {
            if (frustum.isVisible(portal.getBoundingBox())) {
                portal.render(
                        levelRenderer,
                        tickDelta,
                        limitTime,
                        camera,
                        gameRenderer,
                        lightTexture,
                        projectionMatrix
                );
            }
        });

        portals.clear();
    }
}
