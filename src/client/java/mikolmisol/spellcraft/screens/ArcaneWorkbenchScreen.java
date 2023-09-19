package mikolmisol.spellcraft.screens;

import mikolmisol.spellcraft.menus.arcane_workbench.ArcaneWorkbenchMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchMenu> {
    private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/crafting_table.png");

    public ArcaneWorkbenchScreen(ArcaneWorkbenchMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float tickDelta, int mouseX, int mouseY) {
        final var x = leftPos;
        final var y = (height - imageHeight) / 2;

        graphics.blit(CRAFTING_TABLE_LOCATION, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float tickDelta) {
        renderBackground(graphics);
        super.render(graphics, x, y, tickDelta);
        renderTooltip(graphics, x, y);
    }
}
