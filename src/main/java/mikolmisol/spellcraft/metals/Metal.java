package mikolmisol.spellcraft.metals;

import com.google.common.collect.Lists;
import lombok.Getter;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.items.SpellcraftItems;
import mikolmisol.spellcraft.util.HashBiMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum Metal {
    IRON("iron", true, Items.RAW_IRON, true),
    GOLD("gold", true, Items.RAW_GOLD, true),
    COPPER("copper", true, Items.RAW_COPPER, true),

    BISMUTH("bismuth", false, SpellcraftItems.GALENA, true),
    LEAD("lead", false, SpellcraftItems.GALENA, false),
    SILVER("silver", false, SpellcraftItems.GALENA, true),
    ELECTRUM("electrum", false, null, false),
    SOULFORGED_STEEL("soulforged_steel", false, null, false);

    private static final HashBiMap<Metal, Variant, List<TagKey<Item>>> TAG_KEYS = new HashBiMap<>();

    private static final HashBiMap<Metal, Variant, ResourceLocation> IDENTIFIERS = new HashBiMap<>();

    private static final HashBiMap<Metal, Variant, Item> ITEMS = new HashBiMap<>();

    @Getter
    private final String name;

    @Getter
    private final boolean vanilla;

    @Getter
    private final @Nullable Item rawItem;

    @Getter
    private final boolean workableByForgeHammer;

    Metal(String name, boolean vanilla, @Nullable Item rawItem, boolean workableByForgeHammer) {
        this.name = name;
        this.vanilla = vanilla;
        this.rawItem = rawItem;
        this.workableByForgeHammer = workableByForgeHammer;
    }

    public @NotNull ResourceLocation getIdentifier(@NotNull Variant variant) {
        return IDENTIFIERS.computeIfAbsent(
                this,
                variant,
                (a, b) -> switch (variant) {
                    case NUGGET ->
                            this == COPPER ? new ResourceLocation(Spellcraft.MOD_ID, String.format(variant.getPattern(), name))
                                           : createIdentifier(variant.getPattern());

                    case INGOT -> createIdentifier(variant.getPattern());

                    case BLOCK -> createIdentifier(variant.getPattern());

                    default -> new ResourceLocation(
                            Spellcraft.MOD_ID,
                            String.format(variant.getPattern(), name)
                    );
                }
        );
    }

    public @NotNull Item getItem(@NotNull Variant variant) {
        return ITEMS.computeIfAbsent(
                this,
                variant,
                (a, b) -> {
                    final var identifier = getIdentifier(variant);
                    return BuiltInRegistries.ITEM.get(identifier);
                }
        );
    }

    public @NotNull List<TagKey<Item>> getTagKeys(@NotNull Variant variant) {
        return TAG_KEYS.computeIfAbsent(
                this,
                variant,
                (a, b) -> {
                    final var tagKeys = Lists.<TagKey<Item>>newArrayList();

                    for (final var commonPattern : variant.getTagPatterns()) {
                        tagKeys.add(
                                TagKey.create(
                                        Registries.ITEM,
                                        new ResourceLocation("c", String.format(commonPattern, name))
                                )
                        );
                    }

                    return tagKeys;
                }
        );
    }

    private ResourceLocation createIdentifier(String pattern) {
        return vanilla ? new ResourceLocation(String.format(pattern, name))
                : new ResourceLocation(Spellcraft.MOD_ID, String.format(pattern, name));
    }

    public boolean isAdded(@NotNull Variant variant) {
        return switch (variant) {
            case NUGGET -> {
                if (this == COPPER) {
                    yield true;
                }

                yield !vanilla;
            }
            case INGOT -> !vanilla;
            case BLOCK -> !vanilla;
            default -> true;
        };
    }
}
