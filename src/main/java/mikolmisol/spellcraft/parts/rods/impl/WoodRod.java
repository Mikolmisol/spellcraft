package mikolmisol.spellcraft.parts.rods.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.parts.rods.Rod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public final class WoodRod implements Rod {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "wood");

    private static final ItemStack INGREDIENT = new ItemStack(Items.OAK_WOOD);

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
    public void onEnchantedBroomHitBlock(@NotNull ArcaneBroom broom, @NotNull BlockHitResult hit) {
        Rod.super.onEnchantedBroomHitBlock(broom, hit);
    }

    @Override
    public void onEnchantedBroomHitEntity(@NotNull ArcaneBroom broom, @NotNull EntityHitResult hit) {
        Rod.super.onEnchantedBroomHitEntity(broom, hit);
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
