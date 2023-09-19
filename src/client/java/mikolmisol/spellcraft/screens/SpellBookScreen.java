package mikolmisol.spellcraft.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/*
public final class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    public SpellBookScreen(SpellBookMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    private void renderBg(PoseStack matrices, float tick, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);

        final var blitOffset = getBlitOffset();

        setBlitOffset(-90);
        blit(matrices, width / 2 - 91, height - 22, 0, 0, 182, 22);
        blit(matrices, width / 2 - 91 - 1 + menu.item.getSelectedSpellIndex(menu.book) * 20, height - 22 - 1, 0, 22, 24, 22);

        setBlitOffset(blitOffset);
    }

    @Override
    public void render(PoseStack matrices, int i, int j, float f) {
        renderBackground(matrices);
        super.render(matrices, i, j, f);
        renderLabels(matrices, i, j);
    }
}
 */
