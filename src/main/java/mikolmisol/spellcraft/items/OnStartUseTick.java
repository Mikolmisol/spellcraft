package mikolmisol.spellcraft.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface OnStartUseTick {

    void onStartUseTick(@NotNull LivingEntity user, @NotNull InteractionHand hand, @NotNull ItemStack item);
}
