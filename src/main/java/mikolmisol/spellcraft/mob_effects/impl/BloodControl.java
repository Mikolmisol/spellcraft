package mikolmisol.spellcraft.mob_effects.impl;

import mikolmisol.spellcraft.accessors.ThrallAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;

public final class BloodControl extends MobEffect {
    public BloodControl() {
        super(MobEffectCategory.HARMFUL, Mth.color(256, 0, 0));
    }

    public static class BloodControlInstance extends MobEffectInstance {
        public BloodControlInstance(MobEffect mobEffect, int durationInTicks) {
            super(mobEffect, durationInTicks);
        }

        @Override
        public boolean tick(LivingEntity livingEntity, Runnable runnable) {
            if (getDuration() == 1) {
                if (livingEntity instanceof ThrallAccessor thrall) {
                    final var master = thrall.spellcraft_getMaster();

                    if (master instanceof Player player && livingEntity instanceof NeutralMob neutralMob) {
                        neutralMob.setPersistentAngerTarget(player.getUUID());
                    }

                    livingEntity.setLastHurtByMob(master);
                }
            }

            return super.tick(livingEntity, runnable);
        }
    }
}
