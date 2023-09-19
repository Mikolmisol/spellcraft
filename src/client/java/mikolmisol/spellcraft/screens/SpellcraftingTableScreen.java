package mikolmisol.spellcraft.screens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.math.Axis;
import lombok.Getter;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.menus.spell_crafting_table.SpellCraftingTableMenu;
import mikolmisol.spellcraft.models.VellumModel;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.render_layers.SpellcraftRenderLayers;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.util.GuiRenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public final class SpellcraftingTableScreen extends AbstractContainerScreen<SpellCraftingTableMenu> {

    private static final ResourceLocation VELLUM = new ResourceLocation(Spellcraft.MOD_ID, "textures/entity/vellum.png");

    private static final ResourceLocation CREATIVE_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private static final ResourceLocation CREATIVE_TABS_LOCATION = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private static final List<Shape> SHAPES = SpellcraftRegistries.SHAPE.stream().toList();

    private static final List<Effect> EFFECTS = SpellcraftRegistries.EFFECT.stream().toList();

    private static final List<Modifier> MODIFIERS = SpellcraftRegistries.MODIFIER.stream().toList();

    private static final int TAB_WIDTH = 26;
    
    private static final int TAB_HEIGHT = 32;
    
    private VellumModel model;

    private Tab selectedTab = Tab.SHAPES;
    
    private boolean scrolling;

    private float scrollOffs;

    public SpellcraftingTableScreen(SpellCraftingTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        model = new VellumModel(minecraft.getEntityModels().bakeLayer(SpellcraftRenderLayers.VELLUM));
    }

    private void renderVellum(GuiGraphics graphics, int mouseX, int mouseY, float tickDelta) {
        final var x = leftPos;
        final var y = height / 2;

        Lighting.setupForEntityInInventory();

        graphics.pose().pushPose();

        graphics.pose().translate(x - 120, y - 200, 100.0f);
        graphics.pose().scale(-40.0f, 40.0f, 40.0f);
        graphics.pose().scale(5f, 5f, 1);
        graphics.pose().mulPose(Axis.XP.rotationDegrees(25));
        graphics.pose().mulPose(Axis.XP.rotationDegrees(-180));
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(180));

        final var vertices = graphics.bufferSource().getBuffer(model.renderType(VELLUM));
        model.renderToBuffer(graphics.pose(), vertices, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        graphics.flush();

        graphics.pose().popPose();

        Lighting.setupFor3DItems();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float tickDelta, int mouseX, int mouseY) {
        renderVellum(graphics, mouseX, mouseY, tickDelta);

        final var x = leftPos;
        final var y = height / 2;

        GuiRenderUtil.renderHotbar(graphics, x, y, imageWidth, imageHeight);

        final var k = leftPos + 175;
        final var l = topPos + 18;
        final var m = l + 112;
        
        graphics.blit(CREATIVE_TABS, k, l + (int)((float)(m - l - 17) * scrollOffs), 232 + 12, 0, 12, 15);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float tickDelta) {
        renderBackground(graphics);
        super.render(graphics, x, y, tickDelta);

        for (final var tab : Tab.values()) {
            if (checkTabHovering(graphics, tab, x, y)) {
                break;
            }
        }

        renderTooltip(graphics, x, y);
    }

    @Override
    public boolean mouseClicked(double mouseClickX, double mouseClickY, int key) {
        if (key == InputConstants.MOUSE_BUTTON_LEFT) {
            final var relativeMouseClickX = mouseClickX - leftPos;
            final var relativeMouseClickY = mouseClickY - topPos;

            for (final var tab : Tab.values()) {
                if (checkTabClicked(tab, relativeMouseClickX, relativeMouseClickY)) {
                    return true;
                }
            }

            if (insideScrollbar(mouseClickX, mouseClickY)) {
                scrolling = true;
                return true;
            }
        }

        return super.mouseClicked(mouseClickX, mouseClickY, key);
    }

    private boolean insideScrollbar(double x, double y) {
        final var i = leftPos;
        final var j = topPos;
        final var k = i + 175;
        final var l = j + 18;
        final var m = k + 14;
        final var n = l + 112;
        
        return x >= k && y >= l && x < m && y < n;
    }

    private boolean checkTabClicked(Tab tab, double clickedX, double clickedY) {
        int tabX = getTabX(tab);
        int tabY = getTabY();
        return clickedX >= tabX && clickedX <= tabX + TAB_WIDTH && clickedY >= tabY && clickedY <= tabY + TAB_HEIGHT;
    }

    private boolean checkTabHovering(GuiGraphics graphics, Tab tab, int mouseX, int mouseY) {
        final var tabX = getTabX(tab);
        final var tabY = getTabY();
        
        if (isHovering(tabX + 3, tabY + 3, 21, 27, mouseX, mouseY)) {
            graphics.renderTooltip(font, tab.getName(), mouseX, mouseY);
            return true;
        }
        return false;
    }
    
    private int getTabX(Tab tab) {
        final var column = tab.ordinal();
        return column * (TAB_WIDTH + 1);
    }
    
    private int getTabY() {
        return imageHeight;
    }
    
    private enum Tab {
        SHAPES("shapes"),
        EFFECTS("effects"),
        MODIFIERS("modifiers");
        
        @Getter
        private final Component name;

        Tab(String name) {
            this.name = Component.translatable(String.format("tab.spellcraft.%s", name));
        }
    }
}
