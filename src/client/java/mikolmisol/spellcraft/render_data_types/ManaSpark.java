package mikolmisol.spellcraft.render_data_types;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ManaSpark(
        @NotNull Vec3 position,
        float size,
        int rayCount
    ) {
}
