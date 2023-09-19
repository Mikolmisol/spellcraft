package mikolmisol.spellcraft.spells.modifiers.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.SpellcraftEffects;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.targets.impl.BlockTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public final class Diffuse implements Modifier {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "diffuse");

    private static final Set<Effect> VALID_EFFECTS = Sets.newHashSet(SpellcraftEffects.BREAK, SpellcraftEffects.HARVEST);

    private static BlockTarget findNeighbour(BlockPos pos, Direction direction, Level level, BlockState original) {
        final var neighbourPos = pos.relative(direction);
        final var state = level.getBlockState(neighbourPos);

        if (state.is(original.getBlock())) {
            return new BlockTarget(neighbourPos, direction);
        }

        return null;
    }

    public static List<BlockTarget> findNeighbours(BlockPos pos, @Nullable Direction castFromDirection, Level level) {
        final var neighbours = Lists.<BlockTarget>newArrayList();
        final var original = level.getBlockState(pos);

        final var directions = Sets.newHashSet(Direction.values());
        if (castFromDirection != null) {
            final var neighbour = findNeighbour(pos, castFromDirection, level, original);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
            directions.remove(castFromDirection);
        }

        for (final var direction : directions) {
            final var neighbour = findNeighbour(pos, direction, level, original);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    @Override
    public @NotNull Component getName() {
        return Component.literal("Diffuse");
    }

    @Override
    public int getMaximumStackSize() {
        return 1;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

}
