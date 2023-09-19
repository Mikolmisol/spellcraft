package mikolmisol.spellcraft.data.tags;

import mikolmisol.spellcraft.items.SpellcraftItems;
import mikolmisol.spellcraft.metals.Metal;
import mikolmisol.spellcraft.metals.Variant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class SpellcraftMetalVariantTagProvider extends FabricTagProvider.ItemTagProvider {

    public SpellcraftMetalVariantTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        for (final var metal : Metal.values()) {
            for (final var variant : Variant.values()) {
                for (final var tagKey : metal.getTagKeys(variant)) {
                    getOrCreateTagBuilder(tagKey)
                            .add(metal.getIdentifier(variant));
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Spellcraft Metal Variant Tags";
    }
}
