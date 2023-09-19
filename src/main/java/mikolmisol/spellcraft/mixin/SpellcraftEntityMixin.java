package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.accessors.ThrallAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class SpellcraftEntityMixin {
    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "getTeam",
                    ret = Team.class
            ),
            cancellable = true
    )
    public Team spellcraft_onGetTeam(CallbackInfoReturnable<Team> callback) {
        if (this instanceof ThrallAccessor thrall) {
            final var master = thrall.spellcraft_getMaster();
            if (master != null) {
                callback.setReturnValue(master.getTeam());
                callback.cancel();
            }
        }

        return null;
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "isAlliedTo",
                    args = Entity.class,
                    ret = boolean.class
            ),
            cancellable = true
    )
    public boolean spellcraft_onIsAlliedTo(Entity entity, CallbackInfoReturnable<Boolean> callback) {
        if (this instanceof ThrallAccessor thrall) {
            final var master = thrall.spellcraft_getMaster();
            if (master != null) {
                if (master.equals(entity)) {
                    callback.setReturnValue(true);
                    callback.cancel();
                } else if (entity instanceof ThrallAccessor alsoThrall) {
                    final var otherMaster = alsoThrall.spellcraft_getMaster();

                    if (otherMaster != null) {
                        callback.setReturnValue(master.isAlliedTo(otherMaster));
                        callback.cancel();
                    }
                } else {
                    callback.setReturnValue(master.isAlliedTo(entity));
                    callback.cancel();
                }
            }
        }

        return false;
    }
}
