package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.accessors.ArcaneShieldAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class GuiRenderUtil {
    private final ResourceLocation MANA_BAR = new ResourceLocation(Spellcraft.MOD_ID, "textures/gui/mana_bar.png");

    private final ResourceLocation ARCANE_SHIELD_BAR = new ResourceLocation(Spellcraft.MOD_ID, "textures/gui/arcane_shield_bar.png");

    private final ResourceLocation HOTBAR = new ResourceLocation(Spellcraft.MOD_ID, "textures/gui/hotbar.png");

    public void renderManaBar(@NotNull GuiGraphics graphics, final double mana, final double maximumMana, float tickDelta, final int screenWidth, final int screenHeight, final Font font) {
        graphics.pose().pushPose();

        final var barX = screenWidth / 2 - 91;
        final var barY = screenHeight / 2 + 50;

        graphics.blit(MANA_BAR, barX, barY, 0, 0, 182, 5);
        graphics.blit(MANA_BAR, barX, barY, 0, 5, (int) (mana / maximumMana * 183), 5);

        final var text = Math.round(mana) + " / " + Math.round(maximumMana);
        final var color = (int) (Mth.lerp(tickDelta, 1176048 - 100, 1176048 + 100));

        final var barXAdjusted = (screenWidth - font.width(text)) / 2;
        graphics.drawString(font, text, barXAdjusted + 1, barY, 0, false);
        graphics.drawString(font, text, barXAdjusted - 1,  barY, 0, false);
        graphics.drawString(font, text, barXAdjusted, barY + 1, 0, false);
        graphics.drawString(font, text, barXAdjusted, barY - 1, 0, false);
        graphics.drawString(font, text, barXAdjusted, barY, color, false);

        graphics.pose().popPose();
    }

    private final float upperBound = 11;

    private float animationProgress = 0;

    public void renderArcaneShieldBar(@NotNull GuiGraphics graphics, int barX, int barY, int jitter, @NotNull ArcaneShieldAccessor shield) {
        animationProgress += 0.15;

        if (animationProgress > upperBound) {
            animationProgress = 0;
        }

        final var tick = Math.round(animationProgress);

        var arcaneShieldStrength = shield.spellcraft_getArcaneShieldStrength();

        final var fullHeartCount = (int) Math.floor(arcaneShieldStrength / 2);

        for (var fullHeart = 0; fullHeart < fullHeartCount; fullHeart += 1) {
            graphics.blit(ARCANE_SHIELD_BAR, barX + fullHeart * 8, barY, tick * 10, 0, 9, 9);
        }

        if (Math.round(arcaneShieldStrength / 2) > fullHeartCount) {
            graphics.blit(ARCANE_SHIELD_BAR, barX + fullHeartCount * 8, barY, tick * 10, 10, 9, 9);
        }
    }

    public void renderHotbar(@NotNull GuiGraphics graphics, int barX, int barY, int imageWidth, int imageHeight) {
        graphics.blit(HOTBAR, barX, barY, 0, 0, imageWidth, imageHeight);
    }
}
