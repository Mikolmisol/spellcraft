package mikolmisol.spellcraft.items.impl;

import mikolmisol.spellcraft.parts.brushes.Brush;
import mikolmisol.spellcraft.parts.knob.Knob;
import mikolmisol.spellcraft.parts.rods.Rod;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneBroomItem extends ManaBarDisplayingItem {

    private static final String TAG_BRUSH = "Brush";

    private static final String TAG_KNOB = "Knob";

    private static final String TAG_ROD = "Rod";

    public ArcaneBroomItem(Properties properties) {
        super(properties);
    }

    public @Nullable Brush getBrush(@NotNull ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return null;
        }

        return SpellcraftRegistries.BRUSH.get(new ResourceLocation(tag.getString(TAG_BRUSH)));
    }

    public @Nullable Knob getKnob(@NotNull ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return null;
        }

        return SpellcraftRegistries.KNOB.get(new ResourceLocation(tag.getString(TAG_KNOB)));
    }

    public @Nullable Rod getRod(@NotNull ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return null;
        }

        return SpellcraftRegistries.ROD.get(new ResourceLocation(tag.getString(TAG_ROD)));
    }
}
