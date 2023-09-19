package mikolmisol.spellcraft.mob_effects.impl;

import mikolmisol.spellcraft.accessors.ArcaneShieldAccessor;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public final class ArcaneShield extends MobEffect {
    public ArcaneShield() {
        super(MobEffectCategory.BENEFICIAL, FastColor.ARGB32.color(256, 256, 0, 256));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int potency) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        final var arcaneShield = JavaUtil.<ArcaneShieldAccessor>cast(entity);

        var arcaneShieldStrength = arcaneShield.spellcraft_getArcaneShieldStrength();
        arcaneShieldStrength += Math.pow(1.25, amplifier) / 20.0;

        arcaneShield.spellcraft_setArcaneShieldStrength(arcaneShieldStrength);
    }
}
