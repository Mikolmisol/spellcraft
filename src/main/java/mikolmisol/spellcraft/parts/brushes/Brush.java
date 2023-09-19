package mikolmisol.spellcraft.parts.brushes;

import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.parts.Part;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public interface Brush extends Part {
    void modifyEnchantedBroomPriorToMounting(@NotNull ArcaneBroom broom);

    void tickEnchantedBroom(@NotNull ArcaneBroom broom);

    default void onEnchantedBroomHitBlock(@NotNull ArcaneBroom broom, @NotNull BlockHitResult hit) {
    }

    default void onEnchantedBroomHitEntity(@NotNull ArcaneBroom broom, @NotNull EntityHitResult hit) {
    }
}
