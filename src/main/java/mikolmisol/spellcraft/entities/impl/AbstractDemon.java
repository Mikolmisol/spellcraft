package mikolmisol.spellcraft.entities.impl;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;

public abstract class AbstractDemon extends Mob {
    public static final MobType DEMON = new MobType();

    protected AbstractDemon(EntityType<? extends AbstractDemon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public MobType getMobType() {
        return DEMON;
    }
}
