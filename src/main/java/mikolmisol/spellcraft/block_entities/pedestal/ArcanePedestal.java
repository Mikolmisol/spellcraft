package mikolmisol.spellcraft.block_entities.pedestal;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import org.jetbrains.annotations.NotNull;

public interface ArcanePedestal {
    @NotNull Storage<ItemVariant> getItemStorageForSpellCrafting();
}
