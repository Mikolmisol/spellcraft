package mikolmisol.spellcraft.render_data_types;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record FluidFace(
        @NotNull Vector3f a,
        @NotNull Vector3f b,
        @NotNull Vector3f c,
        @NotNull Vector3f d,
        @NotNull FluidVariant fluid
        ) {
}
