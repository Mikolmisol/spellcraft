package mikolmisol.spellcraft.data.tags;

import lombok.experimental.UtilityClass;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class SpellcraftTags {

    public static final TagKey<Item> RAW_FISH = TagKey.create(Registries.ITEM, new ResourceLocation("c", "raw_fish"));

    public static final TagKey<Item> FLOUR = TagKey.create(Registries.ITEM, new ResourceLocation("c", "flour"));

    private static void generateDamageTypeTags(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpellcraftDamageTypeTagProvider::new);
    }

    private static void generateItemTags(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpellcraftMetalVariantTagProvider::new);
        pack.addProvider(SpellcraftCommonItemTagProvider::new);
    }

    private static void generateBlockTags(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpellcraftBlockTagProvider::new);
    }

    public void generate(@NotNull FabricDataGenerator.Pack pack) {
        generateDamageTypeTags(pack);
        generateItemTags(pack);
        generateBlockTags(pack);
    }
}
