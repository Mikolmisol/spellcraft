package mikolmisol.spellcraft.block_entity_renderers.font;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.block_entities.font.ArcaneFontBlockEntity;
import mikolmisol.spellcraft.render_data_types.FluidFace;
import mikolmisol.spellcraft.util.FluidRenderUtil;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class ArcaneFontBlockEntityRenderer implements BlockEntityRenderer<ArcaneFontBlockEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public ArcaneFontBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(ArcaneFontBlockEntity font, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int lightTexture, int overlayTexture) {
        final var storage = font.getFluidStorageForSpellCrafting();

        var resource = (ResourceAmount<FluidVariant>) null;

        try (final var transaction = Transaction.openOuter()) {
            resource = StorageUtil.findExtractableContent(storage, transaction);
        }

        if (resource == null) {
            return;
        }

        final var fluid = resource.resource();
        final var fullness = ((double) resource.amount()) / ((double) ArcaneFontBlockEntity.CAPACITY);

        if (fluid.isBlank()) {
            return;
        }

        final var position = font.getBlockPos();
        final var level = font.getLevel();

        final var handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid.getFluid());
        final var defaultFluidState = fluid.getFluid().defaultFluidState();

        final var sprites = handler.getFluidSprites(level, position, defaultFluidState);
        final var stillSprite = sprites[0];
        final var color = handler.getFluidColor(level, position, defaultFluidState);
        lightTexture = LevelRenderer.getLightColor(level, position.above());

        final var centre = Vec3.atBottomCenterOf(position).add(0.5, 1.27 + 0.16 * fullness, 0.5).toVector3f();
        final var a = new Vector3f(centre).add(0.35f, 0, -0.35f);
        final var b = new Vector3f(centre).add(0.35f, 0, 0.35f);
        final var c = new Vector3f(centre).add(-0.35f, 0, 0.35f);
        final var d = new Vector3f(centre).add(-0.35f, 0, -0.35f);

        final var lastPose = matrices.last().pose();

        final var vertices = buffers.getBuffer(RenderType.solid());

        lastPose.translate(Vec3.atCenterOf(position).toVector3f().mul(-1));

        matrices.pushPose();

        RenderSystem.setShaderTexture(0, stillSprite.atlasLocation());

        vertex(vertices, lastPose, d, color, stillSprite.getU0(), stillSprite.getV0(), lightTexture);
        vertex(vertices, lastPose, c, color, stillSprite.getU0(), stillSprite.getV1(), lightTexture);
        vertex(vertices, lastPose, b, color, stillSprite.getU1(), stillSprite.getV1(), lightTexture);
        vertex(vertices, lastPose, a, color, stillSprite.getU1(), stillSprite.getV0(), lightTexture);

        matrices.popPose();

        if (level.random.nextInt(0, 10) > 8) {
            try {
                fluid.getFluid().animateTick(level, position, defaultFluidState, level.random);
            } catch (Throwable t) {
                Spellcraft.LOGGER.warn("An Exception was thrown while animating the fluid "+fluid.getFluid()+" in an Arcane Font at "+position, t);
            }
        }
    }

    private static void vertex(VertexConsumer vertices, Matrix4f projectionMatrix, Vector3f position, int color, float u, float v, int light) {
        vertices.vertex(projectionMatrix, position.x, position.y, position.z)
                .color(color)
                .uv(u, v)
                .uv2(light)
                .normal(0, 1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .endVertex();
    }
}
