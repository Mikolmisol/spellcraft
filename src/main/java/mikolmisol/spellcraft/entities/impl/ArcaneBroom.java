package mikolmisol.spellcraft.entities.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.entities.entity_data.SpellcraftEntityDataSerializers;
import mikolmisol.spellcraft.items.impl.ArcaneBroomItem;
import mikolmisol.spellcraft.parts.brushes.Brush;
import mikolmisol.spellcraft.parts.knob.Knob;
import mikolmisol.spellcraft.parts.rods.Rod;
import mikolmisol.spellcraft.util.GeomUtil;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public final class ArcaneBroom extends FlyingMob {
    private static final String TAG_ITEM = "Item";

    private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(ArcaneBroom.class, EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Double> STEERING = SynchedEntityData.defineId(ArcaneBroom.class, SpellcraftEntityDataSerializers.DOUBLE);

    private static final EntityDataAccessor<Double> LIFT = SynchedEntityData.defineId(ArcaneBroom.class, SpellcraftEntityDataSerializers.DOUBLE);

    private static final EntityDataAccessor<Double> SPEED = SynchedEntityData.defineId(ArcaneBroom.class, SpellcraftEntityDataSerializers.DOUBLE);

    private Knob knob;

    private Rod rod;

    private Brush brush;

    public ArcaneBroom(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static ArcaneBroom createAndMount(@NotNull Level level, @NotNull Entity driver, @NotNull ArcaneBroomItem broom, @NotNull ItemStack item) {
        final var self = new ArcaneBroom(SpellcraftEntityTypes.ARCANE_BROOM, level);

        self.moveTo(driver.position());

        self.entityData.set(ITEM, item);

        self.knob = broom.getKnob(item);

        if (self.knob != null) {
            self.knob.modifyEnchantedBroomPriorToMounting(self);
        }

        self.rod = broom.getRod(item);

        if (self.rod != null) {
            self.rod.modifyEnchantedBroomPriorToMounting(self);
        }

        self.brush = broom.getBrush(item);

        if (self.brush != null) {
            self.brush.modifyEnchantedBroomPriorToMounting(self);
        }

        self.xRotO = driver.xRotO;
        self.setXRot(driver.getXRot());

        self.yRotO = driver.yRotO;
        self.setYRot(driver.getYRot());

        driver.startRiding(self);
        driver.resetFallDistance();

        self.turn(driver.getXRot(), driver.getYRot());

        level.addFreshEntity(self);

        return self;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AttributeSupplier
                .builder()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0)
                .add(Attributes.MOVEMENT_SPEED, 0.6)
                .add(Attributes.FLYING_SPEED, 0.6)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ARMOR_TOUGHNESS, 0)
                .add(Attributes.FOLLOW_RANGE, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }

    private static float lerpRotation(float f, float g) {
        while (g - f < -180.0F) {
            f -= 360.0F;
        }

        while (g - f >= 180.0F) {
            f += 360.0F;
        }

        return Mth.lerp(0.2F, f, g);
    }

    public double getBroomSteering() {
        return entityData.get(STEERING);
    }

    private void setBroomSteering(double steering) {
        entityData.set(STEERING, steering);
    }

    public void increaseBroomSteering(double amount) {
        setBroomSteering(getBroomSteering() + amount);
    }

    public void decreaseBroomSteering(double amount) {
        setBroomSteering(getBroomSteering() - amount);
    }

    public double getBroomLift() {
        return entityData.get(LIFT);
    }

    private void setBroomLift(double lift) {
        entityData.set(LIFT, lift);
    }

    public void increaseBroomLift(double amount) {
        setBroomLift(getBroomLift() + amount);
    }

    public void decreaseBroomLift(double amount) {
        setBroomLift(getBroomLift() - amount);
    }

    public double getBroomSpeed() {
        return entityData.get(SPEED);
    }

    private void setBroomSpeed(double speed) {
        entityData.set(SPEED, speed);
    }

    public void increaseBroomSpeed(double amount) {
        setBroomSpeed(getBroomSpeed() + amount);
    }

    public void decreaseBroomSpeed(double amount) {
        setBroomSpeed(getBroomSpeed() - amount);
    }

    @Override
    public MoveControl getMoveControl() {
        return moveControl;
    }

    @Override
    public void tick() {
        super.tick();

        final var entities = getPassengersAndSelf().toList();

        for (final var entity : entities) {
            entity.resetFallDistance();
        }

        if (knob != null) {
            knob.tickEnchantedBroom(this);
        }

        if (rod != null) {
            rod.tickEnchantedBroom(this);
        }

        if (brush != null) {
            brush.tickEnchantedBroom(this);
        }

        if (!level.isClientSide) {
            final var deltaMovement = getDeltaMovement();
            final var position = position();
            var deltaMovementPlusPosition = position.add(deltaMovement);

            var struckPortal = false;
            var hit = (HitResult) level.clip(new ClipContext(position, deltaMovementPlusPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

            final var entityHitResult = ProjectileUtil.getEntityHitResult(level, this, position, deltaMovementPlusPosition, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0), EntitySelector.NO_SPECTATORS.and(EntitySelector.CAN_BE_COLLIDED_WITH));
            if (entityHitResult != null) {
                hit = entityHitResult;
            }

            if (hit.getType() != HitResult.Type.MISS) {
                if (hit instanceof BlockHitResult blockHit) {
                    final var hitPosition = blockHit.getBlockPos();
                    final var hitBlock = level.getBlockState(hitPosition);

                    if (hitBlock.is(Blocks.NETHER_PORTAL)) {

                        for (final var passenger : getSelfAndPassengers().toList()) {
                            passenger.handleInsidePortal(hitPosition);
                        }

                        struckPortal = true;
                    } else if (hitBlock.is(Blocks.END_GATEWAY)) {

                        final var blockEntity = level.getBlockEntity(hitPosition);
                        if (blockEntity instanceof TheEndGatewayBlockEntity endGateway && TheEndGatewayBlockEntity.canEntityTeleport(this)) {

                            for (final var passenger : getSelfAndPassengers().toList()) {
                                TheEndGatewayBlockEntity.teleportEntity(level, hitPosition, hitBlock, passenger, endGateway);
                            }

                            struckPortal = true;
                        }
                    }

                    if (!struckPortal) {
                        knob.onEnchantedBroomHitBlock(this, blockHit);
                        rod.onEnchantedBroomHitBlock(this, blockHit);
                        brush.onEnchantedBroomHitBlock(this, blockHit);
                    }
                } else if (hit instanceof EntityHitResult entityHit) {
                    knob.onEnchantedBroomHitEntity(this, entityHit);
                    rod.onEnchantedBroomHitEntity(this, entityHit);
                    brush.onEnchantedBroomHitEntity(this, entityHit);
                }
            }

            checkInsideBlocks();
        }

        var deltaMovement = getDeltaMovement();

        var deltaMovementY = deltaMovement.y + Mth.sin(Util.getMillis() / 500f) / 100f;

        final var position = blockPosition();
        final var block = level.getBlockState(position);

        if (!block.isAir()) {
            var pos = position().subtract(0, 0.1, 0);
            final var shape = block.getCollisionShape(level, position);
            if (!shape.isEmpty()) {
                pos = position();

                for (final var box : shape.toAabbs()) {
                    if (box.move(position).contains(pos)) {
                        deltaMovementY = Math.max(0, deltaMovementY);
                        break;
                    }
                }
            }
        }

        final var deltaX = getX() + deltaMovement.x;
        final var deltaY = getY() + deltaMovementY;
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

        deltaMovement = deltaMovement.scale(dampeningForce);

        setDeltaMovement(deltaMovement);

        if (!isNoGravity()) {
            final var preGravityDeltaMovement = getDeltaMovement();
            setDeltaMovement(preGravityDeltaMovement.x, preGravityDeltaMovement.y - getGravity(), preGravityDeltaMovement.z);
        }

        setPos(deltaX, deltaY, deltaZ);
    }

    @Override
    public void travel(Vec3 direction) {
        final var driver = getControllingPassenger();

        if (driver instanceof Player player) {
            final var speed = getBroomSpeed();

            super.travel(direction.add(
                    player.xxa * speed,
                    player.yya * speed,
                    player.zza * speed
            ));

            return;
        }

        super.travel(direction);
    }

    @Override
    public AABB getBoundingBox() {
        final var passengers = getPassengers();

        if (!passengers.isEmpty()) {
            if (passengers.size() == 1) {
                return passengers.get(0).getBoundingBox();
            }

            final var boxes = Lists.newArrayList(getPassengers().stream().map(Entity::getBoundingBox).map(GeomUtil::asBoundingBox).toList());
            boxes.add(GeomUtil.asBoundingBox(super.getBoundingBox()));

            final var box = BoundingBox.encapsulatingBoxes(boxes);

            if (box.isPresent()) {
                return AABB.of(box.get());
            }
        }

        return super.getBoundingBox();
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        final var location = super.getDismountLocationForPassenger(passenger);

        if (passenger instanceof Player player && getPassengers().size() == 1) {
            player.getInventory().placeItemBackInInventory(entityData.get(ITEM));
            discard();
        }

        return location;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        final var navigation = new FlyingPathNavigation(this, level);
        final var driver = getControllingPassenger();

        if (driver instanceof Mob mobDriver) {
            final var nodeEvaluator = mobDriver.getNavigation().getNodeEvaluator();
            navigation.setCanOpenDoors(nodeEvaluator.canOpenDoors());
            navigation.setCanPassDoors(nodeEvaluator.canPassDoors());
        }

        navigation.setCanFloat(false);
        return navigation;
    }

    private void updateRotation() {
        final var passenger = getControllingPassenger();

        if (passenger instanceof Player) {
            setXRot(lerpRotation(xRotO, passenger.getXRot()));
            setYRot(lerpRotation(yRotO, passenger.getYRot()));
        } else if (passenger != null) {
            setXRot(lerpRotation(xRotO, passenger.getXRot()));
            setYRot(lerpRotation(yHeadRotO, passenger.getYHeadRot()));
        }
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return getPassengers().isEmpty();
    }

    @Override
    public boolean hurt(DamageSource damage, float f) {
        if (isInvulnerableTo(damage)) {
            return false;
        }

        if (damage.is(DamageTypes.FALL)) {
            return false;
        }

        if (!level.isClientSide) {
            playSound(SoundEvents.ARMOR_STAND_BREAK, 1.0F, 1.0F);

            if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                spawnAtLocation(entityData.get(ITEM));
            }

            discard();
        }

        return super.hurt(damage, f);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ITEM, null);
        entityData.define(STEERING, 0.1);
        entityData.define(LIFT, 0.1);
        entityData.define(SPEED, 0.1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        final var item = ItemStack.of(tag.getCompound(TAG_ITEM));
        entityData.set(ITEM, item);

        final var broom = (ArcaneBroomItem) item.getItem();

        knob = broom.getKnob(item);
        rod = broom.getRod(item);
        brush = broom.getBrush(item);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.put(TAG_ITEM, entityData.get(ITEM).save(new CompoundTag()));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return isVehicle();
    }

    private double getGravity() {
        return 0.1;
    }

    @Override
    public boolean isPickable() {
        return !isVehicle();
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return entityData.get(ITEM).copy();
    }

    @Override
    public boolean isPushable() {
        return !isVehicle();
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public boolean isPushedByFluid() {
        return !isVehicle();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isVehicle();
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return Boat.canVehicleCollide(this, entity);
    }
}
