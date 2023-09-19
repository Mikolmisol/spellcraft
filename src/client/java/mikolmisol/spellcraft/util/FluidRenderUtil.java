package mikolmisol.spellcraft.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.render_data_types.FluidBlob;
import mikolmisol.spellcraft.render_data_types.FluidFace;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@UtilityClass
public class FluidRenderUtil {
    public void renderFluidFace(@NotNull FluidFace face, @NotNull BlockAndTintGetter level, @NotNull BlockPos blockPosition) {
        final var fluid = face.fluid().getFluid();
        final var handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);

        if (handler == null) {
            return;
        }

        final var sprites = handler.getFluidSprites(level, blockPosition, fluid.defaultFluidState());

        if (sprites == null) {
            return;
        }

        final var color = handler.getFluidColor(level, blockPosition, fluid.defaultFluidState());
        final var stillSprite = Objects.requireNonNull(sprites[0]);
    }

    public void renderFluidBlob(@NotNull FluidBlob blob, @NotNull BlockAndTintGetter level, @NotNull BlockPos blockPosition, @NotNull MultiBufferSource buffers, @NotNull PoseStack matrices) {
        final var fluid = blob.fluid().getFluid();
        final var handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);

        if (handler == null) {
            return;
        }

        final var sprites = handler.getFluidSprites(level, blockPosition, fluid.defaultFluidState());

        if (sprites == null) {
            return;
        }

        final var stillSprite = Objects.requireNonNull(sprites[0]);
        final var u0 = stillSprite.getU0();
        final var u1 = stillSprite.getU1();
        final var v0 = stillSprite.getV0();
        final var v1 = stillSprite.getV1();

        RenderSystem.setShaderTexture(0, stillSprite.atlasLocation());

        final var color = handler.getFluidColor(level, blockPosition, fluid.defaultFluidState());

        final var state = blob.fluid().getFluid().defaultFluidState();

        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();

        final var renderType = ItemBlockRenderTypes.getRenderLayer(state);

        final var vertices = buffers.getBuffer(renderType);

        final var projectionMatrix = matrices.last().pose();
    }

    public void renderFluid(BlockAndTintGetter level, BlockPos blockPosition, Fluid fluid, MultiBufferSource buffers) {

        final var handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);

        if (handler == null) {
            return;
        }

        final var sprites = handler.getFluidSprites(level, blockPosition, fluid.defaultFluidState());

        if (sprites == null) {
            return;
        }

        final var stillSprite = sprites[0];
        final var color = handler.getFluidColor(level, blockPosition, fluid.defaultFluidState());

        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;

        var o = 1.0f;
        var p = 1.0f;
        var q = 1.0f;
        var r = 1.0f;

        final var downShade = level.getShade(Direction.DOWN, true);
        final var upShade = level.getShade(Direction.UP, true);
        final var northShade = level.getShade(Direction.NORTH, true);
        final var westShade = level.getShade(Direction.WEST, true);

        double d = blockPosition.getX() & 0xF;
        double e = blockPosition.getY() & 0xF;
        double w = blockPosition.getZ() & 0xF;

        final var renderType = ItemBlockRenderTypes.getRenderLayer(fluid.defaultFluidState());

        final var vertices = buffers.getBuffer(renderType);

        {
            p -= 0.001f;
            r -= 0.001f;
            q -= 0.001f;
            o -= 0.001f;

            var z = stillSprite.getU(0.0);
            var aa = stillSprite.getV(0.0);
            var ab = z;
            var ac = stillSprite.getV(16.0);
            var ad = stillSprite.getU(16.0);
            var ae = ac;
            var af = ad;
            var ag = aa;
            
            float al = (z + ab + ad + af) / 4.0f;
            final var ah = (aa + ac + ae + ag) / 4.0f;
            final var ai = sprites[0].uvShrinkRatio();

            z = Mth.lerp(ai, z, al);
            ab = Mth.lerp(ai, ab, al);
            ad = Mth.lerp(ai, ad, al);
            af = Mth.lerp(ai, af, al);
            aa = Mth.lerp(ai, aa, ah);
            ac = Mth.lerp(ai, ac, ah);
            ae = Mth.lerp(ai, ae, ah);
            ag = Mth.lerp(ai, ag, ah);

            final var lightTexture = LevelRenderer.getLightColor(level, blockPosition);
            final var ak = upShade * red;
            final var an = upShade * green;
            final var ao = upShade * blue;
            vertex(vertices, d + 0.0, e + (double)p, w + 0.0, ak, an, ao, z, aa, lightTexture);
            vertex(vertices, d + 0.0, e + (double)r, w + 1.0, ak, an, ao, ab, ac, lightTexture);
            vertex(vertices, d + 1.0, e + (double)q, w + 1.0, ak, an, ao, ad, ae, lightTexture);
            vertex(vertices, d + 1.0, e + (double)o, w + 0.0, ak, an, ao, af, ag, lightTexture);

            final var shouldRenderBackwardUpFace = true;

            if (shouldRenderBackwardUpFace) {
                vertex(vertices, d + 0.0, e + (double)p, w + 0.0, ak, an, ao, z, aa, lightTexture);
                vertex(vertices, d + 1.0, e + (double)o, w + 0.0, ak, an, ao, af, ag, lightTexture);
                vertex(vertices, d + 1.0, e + (double)q, w + 1.0, ak, an, ao, ad, ae, lightTexture);
                vertex(vertices, d + 0.0, e + (double)r, w + 1.0, ak, an, ao, ab, ac, lightTexture);
            }
        }

        {
            final var z = sprites[0].getU0();
            final var ab = sprites[0].getU1();
            final var ad = sprites[0].getV0();
            final var af = sprites[0].getV1();
            final var ap = LevelRenderer.getLightColor(level, blockPosition.below());
            final var ac = downShade * red;
            final var ae = downShade * green;
            final var ag = downShade * blue;

            vertex(vertices, d, e, w + 1.0, ac, ae, ag, z, af, ap);
            vertex(vertices, d, e, w, ac, ae, ag, z, ad, ap);
            vertex(vertices, d + 1.0, e, w, ac, ae, ag, ab, ad, ap);
            vertex(vertices, d + 1.0, e, w + 1.0, ac, ae, ag, ab, af, ap);
        }
    }

    private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int lightTexture) {
        vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0f).uv(u, v).uv2(lightTexture).normal(0.0f, 1.0f, 0.0f).endVertex();
    }
}
