package mikolmisol.spellcraft.data.tags;

import mikolmisol.spellcraft.items.SpellcraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public final class SpellcraftCommonItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public SpellcraftCommonItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(SpellcraftTags.RAW_FISH)
                .add(Items.COD)
                .add(Items.TROPICAL_FISH)
                .add(Items.SALMON);

        getOrCreateTagBuilder(SpellcraftTags.FLOUR)
                .add(SpellcraftItems.WHEAT_FLOUR);
    }

    @Override
    public String getName() {
        return "Spellcraft Common Tags";
    }
}
