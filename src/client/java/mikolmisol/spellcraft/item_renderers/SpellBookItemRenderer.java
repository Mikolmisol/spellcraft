package mikolmisol.spellcraft.item_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

/*
public final class SpellBookItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack item, ItemDisplayContext transform, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        if (!(item.getItem() instanceof SpellBook book)) {
            return;
        }

        final var model = book.getModel();

        final var minecraft = Minecraft.getInstance();
        final var tick = minecraft.getFrameTime();

        {
            matrices.pushPose();
            matrices.scale(1.0F, -1.0F, -1.0F);

            var shouldGlow = false;
            if (book.isCastingSpell(item)) {
                if (!model.stepGlow()) {
                    book.stopCastingSpell(item);
                }
            }

            switch (transform) {
                case FIRST_PERSON_LEFT_HAND ->
                        renderInFirstPersonLeftHand(model, item, book, matrices, buffers, tick, shouldGlow, light, overlay);
                case FIRST_PERSON_RIGHT_HAND ->
                        renderInFirstPersonRightHand(model, item, book, matrices, buffers, tick, shouldGlow, light, overlay);
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND ->
                        renderInThirdPerson(model, matrices, buffers, light, overlay);
                case GROUND -> renderOnGround(model, matrices, buffers, light, overlay);
                case FIXED -> renderInItemFrame(model, matrices, buffers, light, overlay);
                default -> {
                    matrices.translate(0.6, -0.4, 0);
                    matrices.mulPose(Axis.YN.rotationDegrees(270f));
                    matrices.mulPose(Axis.XN.rotationDegrees(45f));
                    matrices.mulPose(Axis.YP.rotationDegrees(45f));

                    model.setAnimation(0f, 0f, 0f);

                    final var vertices = buffers.getBuffer(model.renderType(SpellBookModel.BOOK));
                    model.renderToBuffer(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }

            matrices.popPose();
        }
    }

    private void renderInFirstPersonRightHand(SpellBookModel model, ItemStack item, SpellBook book, PoseStack matrices, MultiBufferSource buffers, float tick, boolean shouldGlow, int light, int overlay) {
        matrices.mulPose(Vector3f.YN.rotationDegrees(245));
        matrices.translate(0.3, -0.65, 0.55);
        matrices.scale(0.85f, 0.85f, 0.85f);

        model.stepWithItem(book, item, tick);

        final var vertices = buffers.getBuffer(model.renderType(SpellBookModel.BOOK));
        model.renderToBuffer(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        if (shouldGlow) {
            renderSpellGlow(model, item, book, matrices, buffers, tick);
            renderSpellGlow(model, item, book, matrices, buffers, tick);
        }
    }

    private void renderInFirstPersonLeftHand(SpellBookModel model, ItemStack item, SpellBook book, PoseStack matrices, MultiBufferSource buffers, float tick, boolean shouldGlow, int light, int overlay) {
        matrices.mulPose(Vector3f.YN.rotationDegrees(270));
        matrices.mulPose(Vector3f.YP.rotationDegrees(-40));
        matrices.translate(0.3, -0.2, 0);
        matrices.scale(0.85f, 0.85f, 0.85f);

        model.stepWithItem(book, item, tick);

        final var vertices = buffers.getBuffer(model.renderType(SpellBookModel.BOOK));
        model.renderToBuffer(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        if (shouldGlow) {
            renderSpellGlow(model, item, book, matrices, buffers, tick);
            renderSpellGlow(model, item, book, matrices, buffers, tick);
        }
    }

    private void renderInThirdPerson(SpellBookModel model, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        matrices.translate(0.5, -0.65, -0.5);
        matrices.mulPose(Vector3f.YN.rotationDegrees(270f));
        matrices.scale(0.85f, 0.85f, 0.85f);

        model.setAnimation(0f, 0f, 0.95f);

        final var vertices = buffers.getBuffer(model.renderType(SpellBookModel.BOOK));
        model.renderToBuffer(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderInItemFrame(SpellBookModel model, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        matrices.mulPose(Vector3f.YN.rotationDegrees(270f));
        matrices.mulPose(Vector3f.YP.rotationDegrees(90f));
        matrices.translate(-0.25, 0, 0);
        matrices.scale(1.25F, 1.25f, 1.25f);

        model.setAnimation(0f, 0f, 0f);

        final var vertices = buffers.getBuffer(model.renderType(SpellBookModel.BOOK));
        model.renderToBuffer(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderOnGround(SpellBookModel model, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        matrices.translate(0, -0.5, 0);


        model.setAnimation(0f, 0f, 0f);

        final var vertices = buffers.getBuffer(model.renderType(SpellBookModel.BOOK));
        model.renderToBuffer(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
 */
