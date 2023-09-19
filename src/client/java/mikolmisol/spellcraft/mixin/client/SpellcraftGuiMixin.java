package mikolmisol.spellcraft.mixin.client;

import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.util.GuiRenderUtil;
import mikolmisol.spellcraft.util.JavaUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class SpellcraftGuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "render",
                    args = { net.minecraft.client.gui.GuiGraphics.class, float.class }
            )
    )
    public void spellcraft_onRender(GuiGraphics graphics, float tickDelta, CallbackInfo info) {
        final var minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        if (minecraft.screen != null) {
            return;
        }

        final var screenWidth = graphics.guiWidth();
        final var screenHeight = graphics.guiHeight();

        final var font = minecraft.font;
        final var mana = JavaUtil.<Caster>cast(minecraft.player).getManaStorage();

        if (mana == null) {
            return;
        }

        GuiRenderUtil.renderManaBar(graphics, mana.getAmount(), mana.getCapacity(), tickDelta, screenWidth, screenHeight, font);
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "renderHearts",
                    args = {GuiGraphics.class, Player.class, int.class, int.class, int.class, int.class, float.class, int.class, int.class, int.class, boolean.class}
            )
    )
    private void onRenderHearts(@NotNull GuiGraphics guiGraphics, @NotNull Player player, int barX, int barY, int rowCount, int jitter, float f, int m, int n, int o, boolean bl, CallbackInfo callback) {
        GuiRenderUtil.renderArcaneShieldBar(guiGraphics, barX, barY, jitter, JavaUtil.cast(player));
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "getPlayerVehicleWithHealth",
                    ret = LivingEntity.class
            ),
            cancellable = true
    )
    private LivingEntity spellcraft_onGetPlayerVehicleWithHealth(CallbackInfoReturnable<LivingEntity> callback) {
        final var player = minecraft.player;

        if (player != null) {
            if (player.getVehicle() instanceof ArcaneBroom) {
                callback.setReturnValue(null);
            }
        }

        return null;
    }
}
