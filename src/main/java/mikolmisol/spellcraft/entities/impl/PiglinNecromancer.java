package mikolmisol.spellcraft.entities.impl;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.Level;

public class PiglinNecromancer extends ZombifiedPiglin {
    public PiglinNecromancer(EntityType<? extends ZombifiedPiglin> type, Level level) {
        super(type, level);
    }
}
