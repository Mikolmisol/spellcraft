package mikolmisol.spellcraft.block_entities.barrier;

import com.google.common.collect.Sets;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.storage.WorldlyManaStorage;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import mikolmisol.spellcraft.blocks.projector.ArcaneProjectorBlock;
import mikolmisol.spellcraft.mana.ManaStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public final class ArcaneBarrierBlockEntity extends SpellcraftBlockEntity implements WorldlyManaStorage, ArcaneHoldingCell {
    private static final double MANA_COST_PER_TICK = 0.05;

    private static final double MANA_COST_PER_FORCE_FIELD_BLOCK = 10;

    private static final int MAXIMUM_HEIGHT = 5;

    private final ManaStorage mana = ManaStorage.creative();

    private boolean isComplete;

    private boolean isActive;

    private Set<BlockPos> projectors = Sets.newLinkedHashSet();

    public ArcaneBarrierBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.ARCANE_BARRIER, blockPos, blockState);
    }

    public static void deactivateAllProjectors(LevelAccessor level, ArcaneBarrierBlockEntity barrier) {
        for (final var projectorPosition : barrier.projectors) {
            final var projectorState = level.getBlockState(projectorPosition);

            for (var step = MAXIMUM_HEIGHT; step > 0; step -= 1) {
                final var forceFieldPosition = projectorPosition.above(step);

                if (level.getBlockState(forceFieldPosition).is(SpellcraftBlocks.FORCE_FIELD)) {
                    level.destroyBlock(forceFieldPosition, true);
                }
            }

            if (!projectorState.is(SpellcraftBlocks.ARCANE_PROJECTOR)) {
                continue;
            }

            level.setBlock(
                    projectorPosition,
                    projectorState.setValue(ArcaneProjectorBlock.ACTIVE, false),
                    0b0000001 | 0b0000010 | 0b0001000
            );
        }
    }

    private static boolean isValidArcaneBarrierFrame(Level level, BlockPos position, ArcaneBarrierBlockEntity barrier) {
        return level.getBlockState(position).is(SpellcraftBlocks.ARCANE_PROJECTOR) || position.equals(barrier.getBlockPos());
    }

    private static void findProjector(Level level, BlockPos lastVisitedPosition, Direction fromDirection, ArcaneBarrierBlockEntity barrier) {
        final var projectorOrControllerPosition = lastVisitedPosition.relative(fromDirection);

        if (projectorOrControllerPosition.equals(barrier.getBlockPos())) {
            barrier.isComplete = true;
            return;
        }

        final var projectorState = level.getBlockState(projectorOrControllerPosition);
        if (projectorState.is(SpellcraftBlocks.ARCANE_PROJECTOR)) {
            if (barrier.projectors.add(projectorOrControllerPosition)) {
                findProjectors(level, projectorOrControllerPosition, fromDirection.getOpposite(), barrier);
            }
        }
    }

    private static void findProjectors(Level level, BlockPos lastVisitedPosition, @Nullable Direction fromDirection, ArcaneBarrierBlockEntity barrier) {
        for (final var direction : Direction.Plane.HORIZONTAL) {
            if (barrier.isComplete) {
                return;
            }

            if (direction == fromDirection) {
                continue;
            }

            final var oppositeDirection = direction.getOpposite();
            final var clockwiseDirection = direction.getClockWise();
            final var counterClockwiseDirection = direction.getCounterClockWise();

            final var position = lastVisitedPosition.relative(direction);
            final var oppositePosition = lastVisitedPosition.relative(oppositeDirection);
            final var clockwisePosition = lastVisitedPosition.relative(clockwiseDirection);
            final var counterClockwisePosition = lastVisitedPosition.relative(counterClockwiseDirection);

            final var isPositionValid = isValidArcaneBarrierFrame(level, position, barrier);
            final var isOppositePositionValid = isValidArcaneBarrierFrame(level, oppositePosition, barrier);
            final var isClockwisePositionValid = isValidArcaneBarrierFrame(level, clockwisePosition, barrier);
            final var isCounterClockwisePositionValid = isValidArcaneBarrierFrame(level, counterClockwisePosition, barrier);

            if (isPositionValid && isOppositePositionValid && !isClockwisePositionValid && !isCounterClockwisePositionValid) {
                findProjector(level, lastVisitedPosition, direction, barrier);

            } else if (isPositionValid && !isOppositePositionValid && isClockwisePositionValid && !isCounterClockwisePositionValid) {
                findProjector(level, lastVisitedPosition, direction, barrier);

            } else if (isPositionValid && !isOppositePositionValid && !isClockwisePositionValid && isCounterClockwisePositionValid) {
                findProjector(level, lastVisitedPosition, direction, barrier);

            } else if (!isPositionValid && !isOppositePositionValid && isClockwisePositionValid && isCounterClockwisePositionValid) {
                findProjector(level, lastVisitedPosition, clockwiseDirection, barrier);

            } else if (!isPositionValid && isOppositePositionValid && isClockwisePositionValid && !isCounterClockwisePositionValid) {
                findProjector(level, lastVisitedPosition, clockwiseDirection, barrier);

            } else if (!isPositionValid && isOppositePositionValid && !isClockwisePositionValid && isCounterClockwisePositionValid) {
                findProjector(level, lastVisitedPosition, oppositeDirection, barrier);
            }
        }
    }

    private static void tryGenerateForceField(Level level, BlockPos projectorPosition, ArcaneBarrierBlockEntity barrier) {
        final var mana = barrier.getManaStorage();

        if (mana == null) {
            return;
        }

        for (var step = 1; step < MAXIMUM_HEIGHT + 1; step += 1) {
            final var forceFieldPos = projectorPosition.above(step);
            final var block = level.getBlockState(forceFieldPos);

            if (!block.isAir()) {
                continue;
            }

            /*
            try (final var transaction = Transaction.openOuter()) {
                final var extractedAmount = mana.extract(MANA_COST_PER_FORCE_FIELD_BLOCK, transaction);

                if (!DoubleMath.fuzzyEquals(extractedAmount, MANA_COST_PER_FORCE_FIELD_BLOCK, 0.01)) {
                    return;
                }

                transaction.commit();

                level.setBlock(
                    forceFieldPos,
                    SpellcraftBlocks.FORCE_FIELD
                        .defaultBlockState()
                        .setValue(ArcaneForceField.NORTH, level.getBlockState(forceFieldPos.north()).is(SpellcraftBlocks.FORCE_FIELD))
                        .setValue(ArcaneForceField.SOUTH, level.getBlockState(forceFieldPos.south()).is(SpellcraftBlocks.FORCE_FIELD))
                        .setValue(ArcaneForceField.EAST, level.getBlockState(forceFieldPos.east()).is(SpellcraftBlocks.FORCE_FIELD))
                        .setValue(ArcaneForceField.WEST, level.getBlockState(forceFieldPos.west()).is(SpellcraftBlocks.FORCE_FIELD)),
                    0b0000001 | 0b0000010 | 0b0001000
                );
            }
            catch (IllegalStateException ignored) {
            }
             */
        }
    }

    private static void collapseForceField(Level level, BlockPos projectorPosition) {
        for (var step = MAXIMUM_HEIGHT; step > 0; step -= 1) {
            final var forceFieldPosition = projectorPosition.above(step);

            if (level.getBlockState(forceFieldPosition).is(SpellcraftBlocks.FORCE_FIELD)) {
                level.setBlockAndUpdate(forceFieldPosition, Blocks.AIR.defaultBlockState());
            }
        }
    }

    private static boolean tickManaCost(ArcaneBarrierBlockEntity barrier) {
        final var mana = barrier.getManaStorage();

        if (mana == null) {
            return false;
        }

        return false;
    }

    private static void tickProjectors(Level level, ArcaneBarrierBlockEntity barrier) {
        for (final var projectorPosition : barrier.projectors) {
            final var projectorState = level.getBlockState(projectorPosition);

            if (projectorState.is(SpellcraftBlocks.ARCANE_BARRIER)) {
                if (!tickManaCost(barrier)) {
                    collapseForceField(level, projectorPosition);
                } else {
                    tryGenerateForceField(level, projectorPosition, barrier);
                }
                continue;
            }

            if (!projectorState.is(SpellcraftBlocks.ARCANE_PROJECTOR)) {
                deactivateAllProjectors(level, barrier);
                continue;
            }

            if (projectorState.is(SpellcraftBlocks.ARCANE_PROJECTOR)) {
                if (!tickManaCost(barrier)) {
                    collapseForceField(level, projectorPosition);
                } else {
                    tryGenerateForceField(level, projectorPosition, barrier);
                }
            } else {
                level.setBlock(
                        projectorPosition,
                        projectorState.setValue(ArcaneProjectorBlock.ACTIVE, true),
                        0b0000001 | 0b0000010 | 0b0001000
                );
            }
        }
    }

    private static void silenceEntities(Level level, ArcaneBarrierBlockEntity barrier) {
        final var box = BoundingBox.encapsulatingPositions(barrier.projectors);

        if (box.isEmpty()) {
            return;
        }

        var volume = AABB.of(box.get());
        volume = volume.setMaxY(volume.maxY + 5);
        final var entities = level.getEntities(null, volume);
    }

    public static void serverTick(Level level, BlockPos position, BlockState block, ArcaneBarrierBlockEntity barrier) {
        final var oldProjectors = Sets.newLinkedHashSet(barrier.projectors);
        barrier.projectors.clear();
        barrier.isComplete = false;

        findProjectors(level, position, null, barrier);
        if (barrier.projectors.size() > 0) {
            barrier.projectors.add(position);
        }

        if (!barrier.projectors.containsAll(oldProjectors)) {
            barrier.isComplete = false;
            barrier.projectors = oldProjectors;
            deactivateAllProjectors(level, barrier);
            return;
        }

        if (level.hasNeighborSignal(barrier.getBlockPos())) {
            deactivateAllProjectors(level, barrier);
            return;
        }

        silenceEntities(level, barrier);
        tickProjectors(level, barrier);
    }

    public static void clientTick(Level level, BlockPos position, BlockState state, ArcaneBarrierBlockEntity barrier) {
        barrier.projectors.clear();
        barrier.isComplete = false;
        barrier.isActive = !level.hasNeighborSignal(barrier.getBlockPos());

        findProjectors(level, position, null, barrier);
        if (barrier.projectors.size() > 0) {
            barrier.projectors.add(position);
        }
    }

    @Override
    public @Nullable ManaStorage getManaStorageForDirection(@NotNull Direction direction) {
        if (direction == Direction.UP) {
            return null;
        }
        return mana;
    }

    @Override
    public @Nullable ManaStorage getManaStorage() {
        return mana;
    }

    @Override
    public @Nullable Set<BlockPos> getProjectorPositionsIfComplete() {
        if (isComplete) {
            return projectors;
        }

        return null;
    }
}
