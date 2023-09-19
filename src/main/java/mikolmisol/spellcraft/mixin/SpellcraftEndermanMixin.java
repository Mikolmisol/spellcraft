package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public abstract class SpellcraftEndermanMixin {

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "teleport",
                    ret = boolean.class
            ),
            cancellable = true
    )
    private boolean spellcraft_onTeleport(CallbackInfoReturnable<Boolean> callback) {
        final var self = (EnderMan) (Object) this;
        if (!self.level.isClientSide && self.isAlive()) {
            if (self.getEffect(SpellcraftMobEffects.SILENCE) != null) {
                callback.setReturnValue(false);
                callback.cancel();
            } else if (self.getEffect(SpellcraftMobEffects.BLOOD_CONTROL) != null) {
                callback.setReturnValue(false);
                callback.cancel();
            }
        }

        return false;
    }
}
