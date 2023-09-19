package mikolmisol.spellcraft.recipes.crafters;

import mikolmisol.spellcraft.recipes.SpellRecipe;
import mikolmisol.spellcraft.spells.Caster;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SpellCrafter extends Container {
    static SpellCrafter ofPlayerHand(@NotNull Player player, @NotNull InteractionHand hand) {
        return null;
    }

    static SpellCrafter ofItemsOnGround(@NotNull List<ItemEntity> items) {
        return new ItemsOnGroundSpellCrafter(items);
    }

    boolean canCraft(@NotNull SpellRecipe recipe);

    void craft(@NotNull SpellRecipe recipe, @NotNull Caster caster, @NotNull Level level);
}
