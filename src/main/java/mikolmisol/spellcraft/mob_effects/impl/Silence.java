package mikolmisol.spellcraft.mob_effects.impl;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class Silence extends MobEffect {
    public Silence() {
        super(MobEffectCategory.HARMFUL, Mth.color(256, 0, 256));
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int i, int j) {
        return false;
    }
}
