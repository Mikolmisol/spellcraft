package mikolmisol.spellcraft.entities.impl;

import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.impl.SpellImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class SpellProjectile extends Projectile {

    public static final EntityDataAccessor<Integer> DECIMAL_COLOR = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> DESTRUCTION_TIMER = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> CREATION_TIMER = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);

    public static final int MAXIMUM_DESTRUCTION_TIMER = 10;

    public static final int MAXIMUM_CREATION_TIMER = 3;

    private static final String TAG_DESTRUCTION_TIMER = "SpellcraftDestructionTimer";

    private static final String TAG_CREATION_TIMER = "SpellcraftCreationTimer";

    private static final String TAG_SPELL = "SpellcraftSpell";

    public Consumer<HitResult> onHit;

    public Spell spell;

    private double gravity = 0.1;

    private @Nullable BlockPos blockEntityOwnerPosition;

    public SpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public static SpellProjectile create(final Level level, final @Nullable Caster owner, final Spell spell, final Consumer<HitResult> onHit) {
        final var self = new SpellProjectile(SpellcraftEntityTypes.SPELL_PROJECTILE, level);

        if (owner instanceof Entity entity) {
            self.setOwner(entity);
        } else if (owner instanceof BlockEntity blockEntity) {
            self.setBlockEntityOwnerPosition(blockEntity.getBlockPos());
        }

        final var gravityAttribute = spell.getShape().getAttributes().get(SpellcraftAttributeTypes.GRAVITY);
        if (gravityAttribute != null) {
            self.gravity = gravityAttribute.getGravity();
        }

        self.spell = spell;
        self.onHit = onHit;
        self.entityData.set(DECIMAL_COLOR, spell.getDecimalColor());
        self.incrementCreationTimer();
        return self;
    }

    private void setBlockEntityOwnerPosition(final @NotNull BlockPos position) {
        blockEntityOwnerPosition = position;
    }

    public void directAtTarget(Entity entity) {

    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        incrementDestructionTimer();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHit) {
        if (onHit != null) {
            onHit.accept(blockHit);
        }

        final var direction = blockHit.getDirection();

        setPos(
                position().add(
                        direction.getStepX(),
                        direction.getStepY(),
                        direction.getStepZ()
                )
        );

        super.onHitBlock(blockHit);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHit) {
        if (onHit != null) {
            onHit.accept(entityHit);
        }

        super.onHitEntity(entityHit);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DECIMAL_COLOR, 0);
        entityData.define(DESTRUCTION_TIMER, 0);
        entityData.define(CREATION_TIMER, 0);
    }

    public int getDecimalColor() {
        return entityData.get(DECIMAL_COLOR);
    }

    public int getDestructionTimer() {
        return entityData.get(DESTRUCTION_TIMER);
    }

    private void incrementDestructionTimer() {
        final var timer = getDestructionTimer();

        if (timer > MAXIMUM_DESTRUCTION_TIMER) {
            discard();
        } else {
            entityData.set(DESTRUCTION_TIMER, timer + 1);
        }
    }

    public int getCreationTimer() {
        return entityData.get(CREATION_TIMER);
    }

    private void incrementCreationTimer() {
        final var timer = getCreationTimer();

        if (timer > MAXIMUM_CREATION_TIMER) {
            entityData.set(CREATION_TIMER, 0);
            return;
        }

        entityData.set(CREATION_TIMER, timer + 1);
    }

    private ClipContext.Fluid getFluidClipContext() {
        if (spell != null) {
            final var fluidClippingBehavior = spell.getShape().getAttributes().get(SpellcraftAttributeTypes.FLUID_CLIP_CONTEXT);

            if (fluidClippingBehavior != null) {
                return fluidClippingBehavior.getFluidClippingBehavior();
            }
        }
        return ClipContext.Fluid.NONE;
    }

    private ClipContext.Block getBlockClipContext() {
        if (spell != null) {
            final var blockClippingBehavior = spell.getShape().getAttributes().get(SpellcraftAttributeTypes.BLOCK_CLIP_CONTEXT);

            if (blockClippingBehavior != null) {
                return blockClippingBehavior.getBlockClippingBehavior();
            }
        }
        return ClipContext.Block.OUTLINE;
    }

    public void tick() {
        super.tick();

        if (getCreationTimer() > 0) {
            incrementCreationTimer();
        }

        if (getDestructionTimer() > 0) {
            incrementDestructionTimer();
            return;
        }

        if (!level.isClientSide) {

            final var deltaMovement = getDeltaMovement();
            final var position = position();
            var deltaMovementPlusPosition = position.add(deltaMovement);

            HitResult hit = level.clip(new ClipContext(position, deltaMovementPlusPosition, getBlockClipContext(), getFluidClipContext(), this));
            if (hit.getType() != HitResult.Type.MISS) {
                deltaMovementPlusPosition = hit.getLocation();
            }

            final var entityHitResult = ProjectileUtil.getEntityHitResult(level, this, position, deltaMovementPlusPosition, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0), this::canHitEntity);
            if (entityHitResult != null) {
                hit = entityHitResult;
            }

            var struckPortal = false;
            var struckOwnerBlockEntity = false;

            if (hit instanceof BlockHitResult blockHit) {
                final var hitPosition = blockHit.getBlockPos();
                final var hitBlock = level.getBlockState(hitPosition);

                if (hitBlock.is(Blocks.NETHER_PORTAL)) {

                    handleInsidePortal(hitPosition);
                    struckPortal = true;

                } else if (hitBlock.is(Blocks.END_GATEWAY)) {

                    final var blockEntity = level.getBlockEntity(hitPosition);
                    if (blockEntity instanceof TheEndGatewayBlockEntity endGateway && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                        TheEndGatewayBlockEntity.teleportEntity(level, hitPosition, hitBlock, this, endGateway);
                    }

                    struckPortal = true;
                }

                if (blockEntityOwnerPosition != null && blockEntityOwnerPosition.equals(hitPosition)) {
                    struckOwnerBlockEntity = true;
                }
            }

            if (hit.getType() != HitResult.Type.MISS && !struckPortal && !struckOwnerBlockEntity) {
                onHit(hit);
            }

            checkInsideBlocks();
        }

        final var deltaMovement = getDeltaMovement();
        final var deltaX = getX() + deltaMovement.x;
        final var deltaY = getY() + deltaMovement.y;
        final var deltaZ = getZ() + deltaMovement.z;
        updateRotation();

        var dampeningForce = 0.99f;

        if (isInWater()) {
            if (!level.isClientSide) {
                for (var count = 0; count < 4; count += 1) {
                    level.addParticle(ParticleTypes.BUBBLE, deltaX - deltaMovement.x * 0.25, deltaY - deltaMovement.y * 0.25, deltaZ - deltaMovement.z * 0.25, deltaMovement.x, deltaMovement.y, deltaMovement.z);
                }
            }

            dampeningForce = 0.8f;
        }

        setDeltaMovement(deltaMovement.scale(dampeningForce));

        if (!isNoGravity()) {
            final var preGravityDeltaMovement = getDeltaMovement();
            setDeltaMovement(preGravityDeltaMovement.x, preGravityDeltaMovement.y - gravity, preGravityDeltaMovement.z);
        }

        setPos(deltaX, deltaY, deltaZ);

        final var color = getDecimalColor();
        final var red = (color & 16711680) >> 16;
        final var green = (color & '\uff00') >> 8;
        final var blue = color & 255;

        final var textureDiffuseRed = red / 255f;
        final var textureDiffuseGreen = green / 255f;
        final var textureDiffuseBlue = blue / 255f;

        level.addParticle(ParticleTypes.ENTITY_EFFECT, deltaX - deltaMovement.x * 0.25, deltaY - deltaMovement.y * 0.25, deltaZ - deltaMovement.z * 0.25, textureDiffuseRed, textureDiffuseGreen, textureDiffuseBlue);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(TAG_DESTRUCTION_TIMER, getDestructionTimer());
        tag.put(TAG_SPELL, Spell.toTag(spell));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        entityData.set(DESTRUCTION_TIMER, Mth.clamp(tag.getInt(TAG_DESTRUCTION_TIMER), 0, MAXIMUM_DESTRUCTION_TIMER));
        spell = Spell.fromTag(tag);
        entityData.set(DECIMAL_COLOR, spell.getDecimalColor());
    }
}
