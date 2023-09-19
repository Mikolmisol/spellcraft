package mikolmisol.spellcraft.parts.brushes.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.parts.brushes.Brush;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public final class HayBrush implements Brush {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "hay");

    private static final ItemStack INGREDIENT = new ItemStack(Items.HAY_BLOCK);

    @Override
    public void modifyEnchantedBroomPriorToMounting(@NotNull ArcaneBroom broom) {
        broom.increaseBroomSpeed(0.2);

        broom.decreaseBroomLift(0.05);
        broom.decreaseBroomSteering(0.05);
    }

    @Override
    public void tickEnchantedBroom(@NotNull ArcaneBroom broom) {

    }

    @Override
    public @NotNull ItemStack getIngredient() {
        return INGREDIENT;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }
}
