package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.items.SelectableItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public abstract class SpellcraftPlayerInventoryMixin {
    @Shadow
    public abstract ItemStack getSelected();

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "swapPaint",
                    args = double.class
            )
    )
    public void spellcraft_onSwapPaint(double d, CallbackInfo callback) {
        final var item = getSelected();

        if (item.getItem() instanceof SelectableItem) {

        }
    }
}
