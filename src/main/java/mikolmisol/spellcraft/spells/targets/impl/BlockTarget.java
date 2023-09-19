package mikolmisol.spellcraft.spells.targets.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BlockTarget(@NotNull BlockPos position, @Nullable Direction direction) {
}
