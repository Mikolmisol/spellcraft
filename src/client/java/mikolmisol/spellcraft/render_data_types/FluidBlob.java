package mikolmisol.spellcraft.render_data_types;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record FluidBlob(
        @NotNull Vector3f bottomA,
        @NotNull Vector3f bottomB,
        @NotNull Vector3f bottomC,
        @NotNull Vector3f bottomD,
        @NotNull Vector3f topA,
        @NotNull Vector3f topB,
        @NotNull Vector3f topC,
        @NotNull Vector3f topD,
        @NotNull FluidVariant fluid
        ) {
}
