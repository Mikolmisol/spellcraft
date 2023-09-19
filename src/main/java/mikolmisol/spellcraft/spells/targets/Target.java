package mikolmisol.spellcraft.spells.targets;

import mikolmisol.spellcraft.spells.targets.impl.BlockTarget;
import mikolmisol.spellcraft.spells.targets.impl.TargetImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Target<T> {
    static Target<ItemStack> ofItem(@NotNull ItemStack item) {
        return new TargetImpl<>(item, SpellcraftTargetTypes.ITEM);
    }

    static Target<BlockTarget> ofBlock(@NotNull BlockPos position, @Nullable Direction direction) {
        return new TargetImpl<>(new BlockTarget(position, direction), SpellcraftTargetTypes.BLOCK);
    }

    static Target<BlockTarget> ofBlock(@NotNull BlockPos position) {
        return ofBlock(position, null);
    }

    static Target<Entity> ofEntity(@NotNull Entity entity) {
        return new TargetImpl<>(entity, SpellcraftTargetTypes.ENTITY);
    }

    @NotNull T getValue();

    @NotNull TargetType getType();
}
