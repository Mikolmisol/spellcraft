package mikolmisol.spellcraft.entities.impl;

import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.EnumSet;

public final class ManaSlime extends PathfinderMob {
    public static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(ManaSlime.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> SATURATION = SynchedEntityData.defineId(ManaSlime.class, EntityDataSerializers.FLOAT);
    private static final float MAXIMUM_SIZE = 10;
    private static final float MAXIMUM_SATURATION = 10;
    private static final String TAG_SIZE = "SpellcraftSize";
    private static final String TAG_SATURATION = "SpellcraftSaturation";

    public ManaSlime(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        moveControl = new ManaSlimeMoveControl();
        registerGoals();
    }

    public static ManaSlime create(Level level) {
        return new ManaSlime(SpellcraftEntityTypes.MANA_SLIME, level);
    }

    private static boolean canEatCake(BlockState block, GameRules rules) {
        if (!rules.getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
        }

        if (block.is(Blocks.CAKE)) {
            return true;
        }

        return block.is(Blocks.CANDLE_CAKE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SIZE, 1.0f);
        entityData.define(SATURATION, 0.0f);
    }

    public float getSize() {
        return entityData.get(SIZE);
    }

    private void setSize(float size) {
        entityData.set(SIZE, size);
    }

    public float getSaturation() {
        return entityData.get(SATURATION);
    }

    private void setSaturation(float saturation) {
        entityData.set(SATURATION, saturation);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat(TAG_SIZE, getSize());
        tag.putFloat(TAG_SATURATION, getSaturation());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSize(tag.getFloat(TAG_SIZE));
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new ManaSlimeFloatGoal());
        goalSelector.addGoal(1, new ManaSlimeApproachCakeGoal());
        goalSelector.addGoal(2, new ManaSlimeApproachFoodItemsGoal());
        goalSelector.addGoal(3, new ManaSlimeWanderGoal());
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        if (ManaSlimeApproachFoodItemsGoal.isValidFoodItem(itemEntity) && getSaturation() < MAXIMUM_SATURATION) {
            final var itemStack = itemEntity.getItem();
            final var sound = itemStack.getEatingSound();

            if (itemStack.getCount() > 1) {
                spawnAtLocation(itemStack.split(itemStack.getCount() - 1));
            }

            onItemPickup(itemEntity);
            itemEntity.discard();
            take(itemEntity, itemStack.getCount());

            setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));

            final var foodProperties = itemStack.getItem().getFoodProperties();
            setSaturation(Math.min(MAXIMUM_SATURATION, getSaturation() + foodProperties.getNutrition() * foodProperties.getSaturationModifier()));
        }
    }

    private void eatCake(BlockPos position, BlockState cake) {
        if (getSaturation() < MAXIMUM_SATURATION) {
            final var bites = cake.getValue(CakeBlock.BITES);
            if (bites < CakeBlock.MAX_BITES) {
                level.setBlock(position, cake.getBlock().defaultBlockState().setValue(CakeBlock.BITES, bites + 1), 3);
            } else {
                level.removeBlock(position, false);
                level.gameEvent(null, GameEvent.BLOCK_DESTROY, position);
            }

            level.playSound(null, position, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, getSoundVolume(), getSoundPitch());
            setSaturation(Math.min(MAXIMUM_SATURATION, getSaturation() + 2f));
        }
    }

    @Override
    public void tick() {
        super.tick();

        final var saturation = getSaturation();

        if (saturation > 0) {
            final var size = getSize();

            if (size < MAXIMUM_SIZE) {
                setSaturation(Math.max(0, saturation - 0.1f));
                setSize(Math.min(MAXIMUM_SIZE, size + 0.1f));

                final var maximumHealth = getAttribute(Attributes.MAX_HEALTH);
                maximumHealth.setBaseValue(maximumHealth.getValue() + 0.1);

                final var movementSpeed = getAttribute(Attributes.MOVEMENT_SPEED);
                movementSpeed.setBaseValue(movementSpeed.getValue() + 0.1);
            }
        }

        final var position = blockPosition();
        final var block = level.getBlockState(position);

        if (canEatCake(block, level.getGameRules())) {
            eatCake(position, block);
        }
    }

    private SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP_SMALL;
    }

    @Override
    protected float getSoundVolume() {
        return getSize();
    }

    private float getSoundPitch() {
        return getSize();
    }

    private int getJumpDelay() {
        return random.nextInt(30) + 10;
    }

    private class ManaSlimeMoveControl extends MoveControl {
        private final ManaSlime slime = ManaSlime.this;
        private float yRot;
        private int jumpDelay;

        public ManaSlimeMoveControl() {
            super(ManaSlime.this);
            yRot = 180.0F * slime.getYRot() / 3.1415927F;
        }

        public void setDirection(float f) {
            yRot = f;
        }

        public void tick() {
            mob.setYRot(rotlerp(mob.getYRot(), yRot, 90.0F));
            mob.yHeadRot = mob.getYRot();
            mob.yBodyRot = mob.getYRot();

            if (operation != Operation.MOVE_TO) {
                mob.setZza(0.0F);

            } else {
                operation = Operation.WAIT;

                if (mob.onGround()) {
                    mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (jumpDelay-- <= 0) {
                        jumpDelay = slime.getJumpDelay();

                        slime.getJumpControl().jump();
                        slime.playSound(slime.getJumpSound(), slime.getSoundVolume(), slime.getSoundPitch());
                    } else {

                        slime.xxa = 0.0F;
                        slime.zza = 0.0F;
                        mob.setSpeed(0.0F);
                    }
                } else {
                    mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    private class ManaSlimeFloatGoal extends FloatGoal {
        private final ManaSlime slime = ManaSlime.this;

        public ManaSlimeFloatGoal() {
            super(ManaSlime.this);
        }

        @Override
        public boolean canUse() {
            return slime.isInWater() || slime.isInLava();
        }
    }

    private class ManaSlimeApproachFoodItemsGoal extends Goal {
        private final ManaSlime slime = ManaSlime.this;

        private static boolean isValidFoodItem(ItemEntity itemEntity) {
            final var itemStack = itemEntity.getItem();
            final var item = itemStack.getItem();
            return item.isEdible();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return false;
        }

        @Override
        public void start() {
            super.start();
            setFlags(EnumSet.of(Flag.TARGET));

            final var foodItemsInRange = slime.level.getEntitiesOfClass(ItemEntity.class, slime.getBoundingBox().inflate(10), ManaSlimeApproachFoodItemsGoal::isValidFoodItem);
            if (foodItemsInRange.size() > 0) {
                slime.getNavigation().moveTo(foodItemsInRange.get(0), 2);
            }
        }

        @Override
        public void tick() {
            final var foodItemsInRange = slime.level.getEntitiesOfClass(ItemEntity.class, slime.getBoundingBox().inflate(10), ManaSlimeApproachFoodItemsGoal::isValidFoodItem);
            if (foodItemsInRange.size() > 0) {
                slime.getNavigation().moveTo(foodItemsInRange.get(0), 2);
            }
        }

        @Override
        public boolean canUse() {
            if (getSaturation() > 0) {
                return false;
            }

            final var foodItemsInRange = slime.level.getEntitiesOfClass(ItemEntity.class, slime.getBoundingBox().inflate(10), ManaSlimeApproachFoodItemsGoal::isValidFoodItem);

            return foodItemsInRange.size() > 0;
        }
    }

    private class ManaSlimeApproachCakeGoal extends MoveToBlockGoal {
        private final ManaSlime slime = ManaSlime.this;

        public ManaSlimeApproachCakeGoal() {
            super(ManaSlime.this, 2, 1);
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos position) {
            return level.getBlockState(position).is(Blocks.CAKE);
        }
    }

    private class ManaSlimeWanderGoal extends Goal {
        private final ManaSlime slime = ManaSlime.this;

        public ManaSlimeWanderGoal() {
            super();
            setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public boolean isInterruptable() {
            return true;
        }
    }
}
