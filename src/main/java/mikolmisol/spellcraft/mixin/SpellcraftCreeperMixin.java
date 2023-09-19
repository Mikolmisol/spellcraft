package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class SpellcraftCreeperMixin extends Monster implements PowerableMob {
    @Shadow
    @Final
    private static EntityDataAccessor<Boolean> DATA_IS_IGNITED;

    @Shadow
    private int swell;

    protected SpellcraftCreeperMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "explodeCreeper"
            ),
            cancellable = true
    )
    private void spellcraft_onExplodeCreeper(CallbackInfo callback) {
        final var self = (Creeper) (Object) this;
        if (!self.level.isClientSide && self.isAlive()) {
            if (self.getEffect(SpellcraftMobEffects.SILENCE) != null) {
                entityData.set(DATA_IS_IGNITED, false);
                swell = 0;
                callback.cancel();
            }
        }
    }
}
