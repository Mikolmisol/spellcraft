package mikolmisol.spellcraft.accessors;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface ThrallAccessor {
    @Nullable LivingEntity spellcraft_getMaster();

    void spellcraft_setMaster(@Nullable LivingEntity master);
}
