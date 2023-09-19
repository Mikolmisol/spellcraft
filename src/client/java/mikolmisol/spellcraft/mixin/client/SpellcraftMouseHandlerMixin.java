package mikolmisol.spellcraft.mixin.client;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.SpellcraftClient;
import mikolmisol.spellcraft.items.ScrollableItem;
import mikolmisol.spellcraft.networking.SpellcraftClientNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class SpellcraftMouseHandlerMixin {
    @Shadow
    private double accumulatedScroll;

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "onScroll",
                    args = {long.class, double.class, double.class}
            ),
            cancellable = true
    )
    private void spellcraft_onScroll(long l, double d, double e, CallbackInfo callback) {
        final var minecraft = Minecraft.getInstance();
        final var player = minecraft.player;

        if (player != null && player.isSecondaryUseActive()) {
            final var items = Lists.newArrayList(player.getMainHandItem(), player.getOffhandItem());
            for (final var item : items) {
                if (item.getItem() instanceof ScrollableItem) {
                    final var scrollDelta = (minecraft.options.discreteMouseScroll().get() ? Math.signum(e) : e) * minecraft.options.mouseWheelSensitivity().get();

                    var accumulatedScroll = this.accumulatedScroll;
                    if (accumulatedScroll != 0.0 && Math.signum(scrollDelta) != Math.signum(accumulatedScroll)) {
                        accumulatedScroll = 0.0;
                    }

                    accumulatedScroll += scrollDelta;
                    final var integralAccumulatedScroll = (int) accumulatedScroll;

                    if (integralAccumulatedScroll != 0) {
                        SpellcraftClientNetworking.sendShiftScrollPacket(integralAccumulatedScroll);
                        callback.cancel();
                    }

                    break;
                }
            }
        }
    }
}
