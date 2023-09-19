package mikolmisol.spellcraft.mob_effects.impl;

import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class Polymorph extends MobEffect {
    public Polymorph() {
        super(MobEffectCategory.HARMFUL, FastColor.ARGB32.color(255, 255, 255, 255));
    }
}
