package mikolmisol.spellcraft.entities.impl;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.level.Level;

public class PiglinPyromancer extends AbstractPiglin {
    public PiglinPyromancer(EntityType<? extends AbstractPiglin> type, Level level) {
        super(type, level);
    }

    @Override
    protected boolean canHunt() {
        return false;
    }

    @Override
    public PiglinArmPose getArmPose() {
        return null;
    }

    @Override
    protected void playConvertedSound() {

    }
}
