package mikolmisol.spellcraft.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class SpellcraftGameRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "renderItemInHand",
                    args = {PoseStack.class, Camera.class, float.class}
            ),
            cancellable = true
    )
    private void spellcraft_onRenderItemInHand(PoseStack poseStack, Camera camera, float f, CallbackInfo callback) {
        if (minecraft.player != null && minecraft.player.hasEffect(SpellcraftMobEffects.POLYMORPH)) {
            callback.cancel();
        }
    }
}
