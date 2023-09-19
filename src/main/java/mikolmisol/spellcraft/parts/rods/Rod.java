package mikolmisol.spellcraft.parts.rods;

import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.parts.Part;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public interface Rod extends Part {
    /**
     * This method is invoked every time a player attempts to cast a spell with a staff
     * that contains this Rod as a component. Since this occurs before any checks are made,
     * it's completely fine to alter the spell's effects, modifiers, or cost.
     */
    void modifySpellPriorToCasting();

    void tickSpellCast();

    void modifyEnchantedBroomPriorToMounting(@NotNull ArcaneBroom broom);

    void tickEnchantedBroom(@NotNull ArcaneBroom broom);

    default void onEnchantedBroomHitBlock(@NotNull ArcaneBroom broom, @NotNull BlockHitResult hit) {
    }

    default void onEnchantedBroomHitEntity(@NotNull ArcaneBroom broom, @NotNull EntityHitResult hit) {
    }

    default @NotNull Vector3f getOffsetForPart(@NotNull Part part) {
        return new Vector3f(0.5f, 0, 0.5f);
    }
}
