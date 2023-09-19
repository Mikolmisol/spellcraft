package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.accessors.ThrallAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PathfinderMob.class)
public abstract class SpellcraftPathfinderMobMixin extends Mob implements ThrallAccessor {
    @Unique
    private LivingEntity master;

    protected SpellcraftPathfinderMobMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @Unique
    public @Nullable LivingEntity spellcraft_getMaster() {
        return master;
    }

    @Override
    @Unique
    public void spellcraft_setMaster(@Nullable LivingEntity master) {
        this.master = master;
    }
}
