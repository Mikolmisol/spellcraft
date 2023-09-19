package mikolmisol.spellcraft.screens;

import mikolmisol.spellcraft.menus.arcane_core.ArcaneCoreMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class ArcaneCoreScreen extends AbstractContainerScreen<ArcaneCoreMenu> {
    public ArcaneCoreScreen(ArcaneCoreMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    private void renderPedestals() {

    }

    private void renderFonts() {

    }

    private void renderCells() {

    }

    private void renderCrafters() {
        final var blockEntityRenderers = minecraft.getBlockEntityRenderDispatcher();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {

    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float tickDelta) {
        renderBackground(graphics);
        super.render(graphics, x, y, tickDelta);
        renderTooltip(graphics, x, y);
    }
}
