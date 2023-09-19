package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.accessors.ThrallAccessor;
import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class SpellcraftMobMixin extends LivingEntity {
    @Unique
    private static final float START_DISTANCE_SQR = 400f;

    protected SpellcraftMobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract @Nullable LivingEntity getTarget();

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity livingEntity);

    @Shadow
    public abstract PathNavigation getNavigation();

    @Inject(
            at = @At("HEAD"),
            target = @Desc("serverAiStep")
    )
    protected final void spellcraft_onServerAiStep(CallbackInfo callback) {
        if (this instanceof ThrallAccessor thrall) {
            if (getEffect(SpellcraftMobEffects.BLOOD_CONTROL) != null) {
                final var master = thrall.spellcraft_getMaster();

                if (master != null) {
                    if (getTarget() != null) {
                        final var target = getTarget();

                        if (!target.isAlive()) {
                            setTarget(null);
                        } else if (!hasLineOfSight(target)) {
                            setTarget(null);
                        } else if (!canAttack(target)) {
                            setTarget(null);
                        }
                    }


                    if (getTarget() == null) {
                        var targetMob = master.getLastHurtByMob();

                        if (targetMob != null && targetMob.isAlive() && !targetMob.is(this) && hasLineOfSight(targetMob) && canAttack(targetMob)) {
                            setTarget(targetMob);
                            spellcraft_alertOtherThralls(thrall, targetMob);
                        } else {
                            targetMob = master.getLastHurtMob();

                            if (targetMob != null && targetMob.isAlive() && !targetMob.is(this) && hasLineOfSight(targetMob) && canAttack(targetMob)) {
                                setTarget(targetMob);
                                spellcraft_alertOtherThralls(thrall, targetMob);
                            } else {
                                spellcraft_findProspectiveAttacker(master);
                            }
                        }
                    }

                    if (distanceToSqr(master) > START_DISTANCE_SQR) {
                        getNavigation().moveTo(master, 1);
                        if (!(JavaUtil.<PathfinderMob>cast(this).isLeashed()) && !isPassenger()) {
                            if (distanceToSqr(master) >= 800) {
                                spellcraft_teleportToMaster(master);
                            }
                        }
                    }
                }
            } else {
                final var master = thrall.spellcraft_getMaster();

                if (master != null) {
                    if (master.isAlive() && hasLineOfSight(master)) {
                        setTarget(master);
                        thrall.spellcraft_setMaster(null);
                    }
                }
            }
        }
    }

    @Unique
    private void spellcraft_teleportToMaster(LivingEntity master) {
        final var masterPosition = master.blockPosition();
        for (var i = 0; i < 10; ++i) {
            var j = random.nextIntBetweenInclusive(-3, 3);
            var k = random.nextIntBetweenInclusive(-3, 3);
            var l = random.nextIntBetweenInclusive(-3, 3);

            var success = spellcraft_tryTeleportToMaster(master, masterPosition.getX() + j, masterPosition.getY() + k, masterPosition.getZ() + l);

            if (success) {
                return;
            }
        }
    }

    @Unique
    private boolean spellcraft_tryTeleportToMaster(LivingEntity master, int i, int j, int k) {
        if (Math.abs(i - master.getX()) < 2.0 && Math.abs(k - master.getZ()) < 2.0) {
            return false;
        } else if (!spellcraft_canTeleportTo(new BlockPos(i, j, k))) {
            return false;
        } else {
            moveTo(i + 0.5, j, k + 0.5, getYRot(), getXRot());
            getNavigation().stop();
            return true;
        }
    }

    @Unique
    private boolean spellcraft_canTeleportTo(BlockPos blockPos) {
        final var blockPathTypes = WalkNodeEvaluator.getBlockPathTypeStatic(level, blockPos.mutable());
        if (blockPathTypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            final var blockState = level.getBlockState(blockPos.below());
            if (!(JavaUtil.cast(this) instanceof FlyingMob || this instanceof FlyingAnimal) && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                final var blockPos2 = blockPos.subtract(blockPosition());
                return level.noCollision(this, getBoundingBox().move(blockPos2));
            }
        }
    }

    @Unique
    private void spellcraft_findProspectiveAttacker(LivingEntity master) {
        final var followRange = getAttributeValue(Attributes.FOLLOW_RANGE);
        final var volume = AABB.unitCubeFromLowerCorner(position()).inflate(followRange, 10, followRange);
        final var entities = level.getEntities(this, volume, EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE));

        for (final var entity : entities) {
            if (!(entity instanceof Mob mob)) {
                continue;
            }

            if (mob.getTarget() == null) {
                continue;
            }

            if (mob.getTarget().is(master) && hasLineOfSight(mob) && canAttack(mob)) {
                setTarget(mob);
                return;
            }

            if (mob.getTarget() instanceof ThrallAccessor thrall && thrall.spellcraft_getMaster() != null && thrall.spellcraft_getMaster().is(master)) {
                setTarget(mob);
                return;
            }
        }
    }

    @Unique
    private void spellcraft_alertOtherThralls(ThrallAccessor thrall, LivingEntity target) {
        final var followRange = getAttributeValue(Attributes.FOLLOW_RANGE);
        final var volume = AABB.unitCubeFromLowerCorner(position()).inflate(followRange, 10, followRange);
        final var entities = level.getEntities(this, volume, EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE));

        for (final var entity : entities) {
            if (!(entity instanceof PathfinderMob otherMob)) {
                continue;
            }

            if (!(entity instanceof ThrallAccessor)) {
                continue;
            }

            if (!(otherMob.isAlliedTo(thrall.spellcraft_getMaster()))) {
                return;
            }

            if (otherMob.getTarget() != null) {
                return;
            }

            if (otherMob.canAttack(target)) {
                otherMob.setTarget(target);
            }
        }
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "addAdditionalSaveData",
                    args = CompoundTag.class
            )
    )
    public void spellcraft_onAddAdditionalSaveData(CompoundTag tag, CallbackInfo callback) {
        if (this instanceof ThrallAccessor thrall) {
            if (thrall.spellcraft_getMaster() instanceof Player player) {
                tag.putUUID("Master", player.getUUID());
            }
        }
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "readAdditionalSaveData",
                    args = CompoundTag.class
            )
    )
    public void spellcraft_onReadAdditionalSaveData(CompoundTag tag, CallbackInfo callback) {
        if (this instanceof ThrallAccessor thrall) {
            if (tag.contains("Master")) {
                try {
                    thrall.spellcraft_setMaster(level.getPlayerByUUID(tag.getUUID("Master")));
                } catch (Throwable ignored) {
                }
            }
        }
    }
}
