package mikolmisol.spellcraft.mixin.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.accessors.PortalsAccessor;
import mikolmisol.spellcraft.portals.Portal;
import mikolmisol.spellcraft.portals.PortalRenderUtil;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Set;

@Mixin(LevelRenderer.class)
public abstract class SpellcraftLevelRendererMixin implements PortalsAccessor, ResourceManagerReloadListener, AutoCloseable {

    @Unique
    private final Map<Vec3, Portal> portals = Maps.newConcurrentMap();

    @Override
    public @NotNull Map<Vec3, Portal> spellcraft_getPortals() {
        return portals;
    }

    @Inject(
            at = @At(
                    value = "CONSTANT",
                    args = "stringValue=destroyProgress",
                    shift = At.Shift.BEFORE
            ),
            target = @Desc(
                    value = "renderLevel",
                    args = {PoseStack.class, float.class, long.class, boolean.class, Camera.class, GameRenderer.class, LightTexture.class, Matrix4f.class}
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void afterBlockEntities(PoseStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo callback, ProfilerFiller profilerFiller, Vec3 vec3, double d, double e, double g, Matrix4f matrix4f2, boolean bl2, Frustum frustum) {
        PortalRenderUtil.renderPortals(tickDelta, limitTime, camera, JavaUtil.cast(this), gameRenderer, lightTexture, matrix4f, frustum, portals);
    }
}
