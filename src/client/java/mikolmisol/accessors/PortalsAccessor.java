package mikolmisol.accessors;

import mikolmisol.spellcraft.portals.Portal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface PortalsAccessor {

    @NotNull Map<Vec3, Portal> spellcraft_getPortals();

}
