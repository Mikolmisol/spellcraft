package mikolmisol.spellcraft.parts.foci.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.parts.foci.Focus;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public final class DiamondFocus implements Focus {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "diamond");

    private static final ItemStack INGREDIENT = new ItemStack(Items.DIAMOND);

    private static final Component DESCRIPTION = Component.translatable("part.focus.spellcraft.diamond");

    @Override
    public void modifySpellPriorToCasting() {

    }

    @Override
    public void tickSpellCast() {

    }

    @Override
    public @NotNull Component getDescription() {
        return DESCRIPTION;
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
