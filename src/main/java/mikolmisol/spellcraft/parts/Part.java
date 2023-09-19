package mikolmisol.spellcraft.parts;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Part {
    @NotNull ItemStack getIngredient();

    @NotNull ResourceLocation getIdentifier();
}
