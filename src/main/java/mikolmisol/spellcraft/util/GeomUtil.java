package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class GeomUtil {

    public boolean isBlockPositionAboveWithinBuildHeightAndReplaceable(@NotNull BlockPlaceContext place) {
        final var placementPosition = place.getClickedPos();
        final var level = place.getLevel();

        return placementPosition.getY() + 1 < level.getMaxBuildHeight()
                && level.getBlockState(placementPosition.above()).canBeReplaced(place);
    }

    public List<BlockPos> getAllBlockPositions(@NotNull AABB box) {
        final var maxX = (int) Math.floor(box.maxX);
        final var maxY = (int) Math.floor(box.maxY);
        final var maxZ = (int) Math.floor(box.maxZ);

        final var positions = new ArrayList<BlockPos>(Math.abs(maxX) * Math.abs(maxY) * Math.abs(maxZ));
        final var cursor = new BlockPos.MutableBlockPos(box.minX, box.minY, box.minZ);

        while (cursor.getX() < maxX + 1) {

            while (cursor.getY() < maxY + 1) {

                while (cursor.getZ() < maxZ + 1) {
                    positions.add(cursor.immutable());
                    cursor.setZ(cursor.getZ() + 1);
                }

                cursor.setZ((int) Math.floor(box.minZ));
                cursor.setY(cursor.getY() + 1);
            }

            cursor.setY((int) Math.floor(box.minY));
            cursor.setX(cursor.getX() + 1);
        }

        return positions;
    }

    public List<BlockPos> getAllBlockPositionsInLineOfSight(@NotNull AABB box, @NotNull Level level, @NotNull Vec3 exactPosition) {
        return getAllBlockPositions(box).stream().filter(position -> {
            final var block = level.getBlockState(position);

            if (block.isAir()) {
                return false;
            }

            final var middlePosition = Vec3.atCenterOf(position);

            final var hit = getBlockInPathToBlock(exactPosition, middlePosition, hitPosition -> {
                final var hitBlock = level.getBlockState(hitPosition);
                final var blockShape = hitBlock.getCollisionShape(level, hitPosition);
                return level.clipWithInteractionOverride(exactPosition, middlePosition, hitPosition, blockShape, hitBlock);
            });

            if (hit.getType() == HitResult.Type.MISS) {
                return true;
            }

            return hit.getBlockPos().equals(position);
        }).toList();
    }

    public List<BlockPos> getAllBlockPositionsInLineOfSight(AABB box, Level level, BlockPos observer) {
        final var exactPosition = Vec3.atCenterOf(observer);

        return getAllBlockPositions(box).stream().filter(position -> {
            final var block = level.getBlockState(position);

            if (block.isAir()) {
                return false;
            }

            final var middlePosition = Vec3.atCenterOf(position);

            final var hit = getBlockInPathToBlock(exactPosition, middlePosition, hitPosition -> {
                if (hitPosition.equals(observer)) {
                    return null;
                }

                final var hitBlock = level.getBlockState(hitPosition);
                final var blockShape = hitBlock.getCollisionShape(level, hitPosition);
                return level.clipWithInteractionOverride(exactPosition, middlePosition, hitPosition, blockShape, hitBlock);
            });

            if (hit.getType() == HitResult.Type.MISS) {
                return true;
            }

            return hit.getBlockPos().equals(position);
        }).toList();
    }

    public List<Entity> getAllEntitiesInLineOfSight(AABB box, Level level, Vec3 exactPosition) {
        return level.getEntities((Entity) null, box, potentialTarget -> {
            if (potentialTarget.isSpectator()) {
                return false;
            }

            final var entityMiddlePosition = potentialTarget.getEyePosition();

            final var hit = getBlockInPathToEntity(
                    new ClipContext(exactPosition, entityMiddlePosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, potentialTarget),
                    (context, hitPosition) -> {
                        final var hitBlock = level.getBlockState(hitPosition);
                        final var hitFluid = level.getFluidState(hitPosition);
                        final var blockShape = context.getBlockShape(hitBlock, level, hitPosition);
                        final var blockHit = level.clipWithInteractionOverride(exactPosition, entityMiddlePosition, hitPosition, blockShape, hitBlock);
                        final var fluidShape = context.getFluidShape(hitFluid, level, hitPosition);
                        final var fluidBlockHit = fluidShape.clip(exactPosition, entityMiddlePosition, hitPosition);
                        final var d = blockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(blockHit.getLocation());
                        final var e = fluidBlockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(fluidBlockHit.getLocation());
                        return d <= e ? blockHit : fluidBlockHit;
                    }
            );

            return hit.getType() == HitResult.Type.MISS;
        });
    }

    public List<Entity> getAllEntitiesInLineOfSight(AABB box, Level level, BlockPos observer) {
        final var exactPosition = Vec3.atCenterOf(observer);

        return level.getEntities((Entity) null, box, potentialTarget -> {
            if (potentialTarget.isSpectator()) {
                return false;
            }

            final var entityMiddlePosition = potentialTarget.getEyePosition();

            final var hit = getBlockInPathToEntity(
                    new ClipContext(exactPosition, entityMiddlePosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, potentialTarget),
                    (context, hitPosition) -> {
                        if (hitPosition.equals(observer)) {
                            return null;
                        }

                        final var hitBlock = level.getBlockState(hitPosition);
                        final var hitFluid = level.getFluidState(hitPosition);
                        final var blockShape = context.getBlockShape(hitBlock, level, hitPosition);
                        final var blockHit = level.clipWithInteractionOverride(exactPosition, entityMiddlePosition, hitPosition, blockShape, hitBlock);
                        final var fluidShape = context.getFluidShape(hitFluid, level, hitPosition);
                        final var fluidBlockHit = fluidShape.clip(exactPosition, entityMiddlePosition, hitPosition);
                        final var d = blockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(blockHit.getLocation());
                        final var e = fluidBlockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(fluidBlockHit.getLocation());
                        return d <= e ? blockHit : fluidBlockHit;
                    }
            );

            return hit.getType() == HitResult.Type.MISS;
        });
    }

    public List<Entity> getAllEntitiesInLineOfSight(AABB box, Level level, Entity observer) {
        final var exactPosition = observer.getEyePosition();

        return level.getEntities(observer, box, potentialTarget -> {
            if (potentialTarget.isSpectator()) {
                return false;
            }

            final var entityMiddlePosition = potentialTarget.getEyePosition();

            final var hit = getBlockInPathToEntity(
                    new ClipContext(exactPosition, entityMiddlePosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, potentialTarget),
                    (context, hitPosition) -> {
                        final var hitBlock = level.getBlockState(hitPosition);
                        final var hitFluid = level.getFluidState(hitPosition);
                        final var blockShape = context.getBlockShape(hitBlock, level, hitPosition);
                        final var blockHit = level.clipWithInteractionOverride(exactPosition, entityMiddlePosition, hitPosition, blockShape, hitBlock);
                        final var fluidShape = context.getFluidShape(hitFluid, level, hitPosition);
                        final var fluidBlockHit = fluidShape.clip(exactPosition, entityMiddlePosition, hitPosition);
                        final var d = blockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(blockHit.getLocation());
                        final var e = fluidBlockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(fluidBlockHit.getLocation());
                        return d <= e ? blockHit : fluidBlockHit;
                    }
            );

            return hit.getType() == HitResult.Type.MISS;
        });
    }

    public boolean isBlockTargetValid(@NotNull Player player, @NotNull BlockPos position) {
        final var exactPosition = player.getEyePosition();
        final var middlePosition = Vec3.atCenterOf(position);

        final BlockHitResult hit;

        try (var level = player.level()) {
            hit = getBlockInPathToBlock(exactPosition, middlePosition, hitPosition -> {
                final var hitBlock = level.getBlockState(hitPosition);
                final var blockShape = hitBlock.getCollisionShape(level, hitPosition);
                return level.clipWithInteractionOverride(exactPosition, middlePosition, hitPosition, blockShape, hitBlock);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hit.getType() == HitResult.Type.MISS;
    }

    public boolean isEntityTargetValid(@NotNull Player player, @NotNull Entity entity) {
        return player.hasLineOfSight(entity);
    }

    private BlockHitResult getBlockInPathToBlock(Vec3 origin, Vec3 end, Function<BlockPos, @Nullable BlockHitResult> onHitBlock) {
        if (origin.equals(end)) {
            final var missPosition = origin.subtract(end);
            return BlockHitResult.miss(end, Direction.getNearest(missPosition.x, missPosition.y, missPosition.z), new BlockPos(asVec3i(end)));
        }

        final var d = Mth.lerp(-1.0E-7, end.x, origin.x);
        final var e = Mth.lerp(-1.0E-7, end.y, origin.y);
        final var f = Mth.lerp(-1.0E-7, end.z, origin.z);
        final var g = Mth.lerp(-1.0E-7, origin.x, end.x);
        final var h = Mth.lerp(-1.0E-7, origin.y, end.y);
        final var i = Mth.lerp(-1.0E-7, origin.z, end.z);

        int j = Mth.floor(g);
        int k = Mth.floor(h);
        int l = Mth.floor(i);

        final var mutableBlockPos = new BlockPos.MutableBlockPos(j, k, l);
        final var firstHit = onHitBlock.apply(mutableBlockPos);

        if (firstHit != null) {
            return firstHit;
        }

        final var m = d - g;
        final var n = e - h;
        final var o = f - i;

        int p = Mth.sign(m);
        int q = Mth.sign(n);
        int r = Mth.sign(o);

        final var s = p == 0 ? Double.MAX_VALUE : (double) p / m;
        final var t = q == 0 ? Double.MAX_VALUE : (double) q / n;
        final var u = r == 0 ? Double.MAX_VALUE : (double) r / o;

        var v = s * (p > 0 ? 1.0 - Mth.frac(g) : Mth.frac(g));
        var w = t * (q > 0 ? 1.0 - Mth.frac(h) : Mth.frac(h));
        var x = u * (r > 0 ? 1.0 - Mth.frac(i) : Mth.frac(i));

        var anotherHit = (BlockHitResult) null;
        do {
            if (!(v <= 1.0) && !(w <= 1.0) && !(x <= 1.0)) {
                final var missPosition = end.subtract(end);
                return BlockHitResult.miss(end, Direction.getNearest(missPosition.x, missPosition.y, missPosition.z), new BlockPos(asVec3i(end)));
            }

            if (v < w) {
                if (v < x) {
                    j += p;
                    v += s;
                } else {
                    l += r;
                    x += u;
                }
            } else if (w < x) {
                k += q;
                w += t;
            } else {
                l += r;
                x += u;
            }

            anotherHit = onHitBlock.apply(mutableBlockPos.set(j, k, l));
        } while (anotherHit == null);

        return anotherHit;
    }

    private BlockHitResult getBlockInPathToEntity(ClipContext context, BiFunction<ClipContext, BlockPos, @Nullable BlockHitResult> onHitEntity) {
        final var origin = context.getFrom();
        final var end = context.getTo();

        if (origin.equals(end)) {
            final var missPosition = context.getFrom().subtract(context.getTo());
            return BlockHitResult.miss(context.getTo(), Direction.getNearest(missPosition.x, missPosition.y, missPosition.z), new BlockPos(asVec3i(context.getTo())));
        }

        final var d = Mth.lerp(-1.0E-7, end.x, origin.x);
        final var e = Mth.lerp(-1.0E-7, end.y, origin.y);
        final var f = Mth.lerp(-1.0E-7, end.z, origin.z);
        final var g = Mth.lerp(-1.0E-7, origin.x, end.x);
        final var h = Mth.lerp(-1.0E-7, origin.y, end.y);
        final var i = Mth.lerp(-1.0E-7, origin.z, end.z);

        int j = Mth.floor(g);
        int k = Mth.floor(h);
        int l = Mth.floor(i);

        final var mutableBlockPos = new BlockPos.MutableBlockPos(j, k, l);
        final var firstHit = onHitEntity.apply(context, mutableBlockPos);

        if (firstHit != null) {
            return firstHit;
        }

        final var m = d - g;
        final var n = e - h;
        final var o = f - i;

        int p = Mth.sign(m);
        int q = Mth.sign(n);
        int r = Mth.sign(o);

        final var s = p == 0 ? Double.MAX_VALUE : (double) p / m;
        final var t = q == 0 ? Double.MAX_VALUE : (double) q / n;
        final var u = r == 0 ? Double.MAX_VALUE : (double) r / o;

        var v = s * (p > 0 ? 1.0 - Mth.frac(g) : Mth.frac(g));
        var w = t * (q > 0 ? 1.0 - Mth.frac(h) : Mth.frac(h));
        var x = u * (r > 0 ? 1.0 - Mth.frac(i) : Mth.frac(i));

        var anotherHit = (BlockHitResult) null;
        do {
            if (!(v <= 1.0) && !(w <= 1.0) && !(x <= 1.0)) {
                final var missPosition = context.getFrom().subtract(context.getTo());
                return BlockHitResult.miss(context.getTo(), Direction.getNearest(missPosition.x, missPosition.y, missPosition.z), new BlockPos(asVec3i(context.getTo())));
            }

            if (v < w) {
                if (v < x) {
                    j += p;
                    v += s;
                } else {
                    l += r;
                    x += u;
                }
            } else if (w < x) {
                k += q;
                w += t;
            } else {
                l += r;
                x += u;
            }

            anotherHit = onHitEntity.apply(context, mutableBlockPos.set(j, k, l));
        } while (anotherHit == null);

        return anotherHit;
    }

    public Vec3i asVec3i(Vec3 vector) {
        return new Vec3i(
                (int) Math.round(vector.x),
                (int) Math.round(vector.y),
                (int) Math.round(vector.z)
        );
    }

    public BoundingBox asBoundingBox(AABB aabb) {
        return new BoundingBox(
                (int) aabb.minX,
                (int) aabb.minY,
                (int) aabb.minZ,
                (int) aabb.maxX,
                (int) aabb.maxY,
                (int) aabb.maxZ
        );
    }
}
