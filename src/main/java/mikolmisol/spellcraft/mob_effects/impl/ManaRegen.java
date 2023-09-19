package mikolmisol.spellcraft.mob_effects.impl;

import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.util.ManaUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public final class ManaRegen extends MobEffect {
    public ManaRegen() {
        super(MobEffectCategory.BENEFICIAL, 1176048);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Caster caster) {
            final var mana = caster.getManaStorage();

            if (mana == null) {
                return;
            }

            ManaUtil.insertManaAndDiscardOverflow(0.25, mana);
        }
    }
}
