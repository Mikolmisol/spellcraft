package mikolmisol.spellcraft.parts.knob.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.parts.knob.Knob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public final class IronKnob implements Knob {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "iron");

    private static final ItemStack INGREDIENT = new ItemStack(Items.IRON_INGOT);

    @Override
    public void modifySpellPriorToCasting() {

    }

    @Override
    public void tickSpellCast() {

    }

    @Override
    public void modifyEnchantedBroomPriorToMounting(@NotNull ArcaneBroom broom) {

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
