package mikolmisol.spellcraft.mob_effects.impl;

import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class GreaterLevitation extends MobEffect {
    public GreaterLevitation() {
        super(MobEffectCategory.BENEFICIAL, FastColor.ARGB32.color(255, 235, 235, 255));
    }
}
