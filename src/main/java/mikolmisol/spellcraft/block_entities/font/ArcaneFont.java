package mikolmisol.spellcraft.block_entities.font;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import org.jetbrains.annotations.NotNull;

public interface ArcaneFont {
    @NotNull Storage<FluidVariant> getFluidStorageForSpellCrafting();
}
