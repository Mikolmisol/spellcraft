package mikolmisol.spellcraft.entities.impl;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public final class ArcaneHorror extends Monster {
    private final ServerBossEvent bossFight;

    public ArcaneHorror(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);
        this.xpReward = Enemy.XP_REWARD_HUGE;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        final var navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanPassDoors(true);
        navigation.setCanFloat(true);
        return navigation;
    }
}
