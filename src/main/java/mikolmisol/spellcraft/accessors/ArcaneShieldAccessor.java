package mikolmisol.spellcraft.accessors;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface ArcaneShieldAccessor {
    void spellcraft_setArcaneShieldStrength(double strength);

    double spellcraft_getArcaneShieldStrength();

    double spellcraft_getMaximumArcaneShieldStrength();

    static void onArcaneShieldDamage(@NotNull LivingEntity entity, @NotNull DamageSource damageSource) {
        if (damageSource.isIndirect()) {
            return;
        }

        final var directEntity = damageSource.getDirectEntity();

        if (directEntity == null) {
            return;
        }

        final var directEntityPosition = directEntity.position();
        final var selfPosition = entity.position();

        final var distance = selfPosition.distanceToSqr(directEntityPosition);
        final var pushVector = selfPosition.vectorTo(directEntityPosition).scale(2 / distance);

        directEntity.push(pushVector.x, 0, pushVector.y);

        final var level = entity.level;

        level.playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.HONEY_BLOCK_HIT,
                entity.getSoundSource(),
                5f,
                5f
        );
    }

    static void onArcaneShieldBreak(@NotNull LivingEntity entity, @NotNull DamageSource damageSource) {
        final var level = entity.level;

        level.playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.GLASS_BREAK,
                entity.getSoundSource(),
                1f,
                1f
        );
    }
}
