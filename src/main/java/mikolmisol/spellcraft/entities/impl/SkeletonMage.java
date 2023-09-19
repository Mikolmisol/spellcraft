package mikolmisol.spellcraft.entities.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.items.SpellContainingItem;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.effects.EffectNode;
import mikolmisol.spellcraft.spells.effects.SpellcraftEffects;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifiers;
import mikolmisol.spellcraft.spells.shapes.ShapeNode;
import mikolmisol.spellcraft.spells.shapes.SpellcraftShapes;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SkeletonMage extends SpellCasterMonster {
    public static final String TAG_LICH_CONVERSION = "LichConversionTime";

    private static final double MANA_REGENERATION = 0.25;

    private static final EntityDataAccessor<Integer> LICH_CONVERSION = SynchedEntityData.defineId(SkeletonMage.class, EntityDataSerializers.INT);

    private int inPowderSnowTime;

    private int conversionTime;

    public SkeletonMage(EntityType<? extends SkeletonMage> entityType, Level level) {
        super(entityType, level);
        this.xpReward = Enemy.XP_REWARD_MEDIUM;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    private static Spell createRandomSpell(RandomSource random, DifficultyInstance difficulty) {
        if (true) {
            return Spell.of(
                    ShapeNode.of(
                            SpellcraftShapes.PROJECTILE,
                            null,
                            Lists.newArrayList(
                                    EffectNode.of(
                                            SpellcraftEffects.FROST,
                                            Lists.newArrayList()
                                    )
                            ),
                            Lists.newArrayList()
                    ),
                    null
            );
        }

        final var possibleShapes = Arrays.asList(SpellcraftShapes.BOLT, SpellcraftShapes.PROJECTILE);
        final var possibleEffects = Arrays.asList(SpellcraftEffects.DRAIN_LIFE, SpellcraftEffects.ENTROPY, SpellcraftEffects.FROST);
        final var effectIndex = random.nextInt(0, possibleEffects.size());

        final var modifiers = Lists.<Modifier>newArrayList();
        for (var index = 0f; index < difficulty.getSpecialMultiplier(); index += 1) {
            if (random.nextBoolean()) {
                modifiers.add(SpellcraftModifiers.POWERFUL);

                if (random.nextBoolean()) {
                    modifiers.add(SpellcraftModifiers.POWERFUL);
                }
            }

            if (random.nextBoolean()) {
                modifiers.add(SpellcraftModifiers.DURATION);
            }
        }

        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LICH_CONVERSION, 0);
    }

    protected void registerGoals() {
        goalSelector.addGoal(2, new RestrictSunGoal(this));
        goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        goalSelector.addGoal(3, new AvoidEntityGoal(this, Wolf.class, 6.0F, 1.0, 1.2));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public boolean isFreezeConverting() {
        return getEntityData().get(LICH_CONVERSION) > 0;
    }

    public void stopFreezeConverting() {
        entityData.set(LICH_CONVERSION, 0);
    }

    public boolean isShaking() {
        return isFreezeConverting();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void aiStep() {
        var shouldBurn = isSunBurnTick();

        if (shouldBurn) {
            final var item = getItemBySlot(EquipmentSlot.HEAD);
            if (!item.isEmpty()) {
                if (item.isDamageableItem()) {
                    item.setDamageValue(item.getDamageValue() + random.nextInt(2));
                    if (item.getDamageValue() >= item.getMaxDamage()) {
                        broadcastBreakEvent(EquipmentSlot.HEAD);
                        setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }

                shouldBurn = false;
            }

            if (shouldBurn) {
                setSecondsOnFire(8);
            }
        }

        super.aiStep();
    }

    public void tick() {
        if (level.isClientSide) {
            super.tick();
            return;
        }

        if (!isAlive()) {
            super.tick();
            return;
        }

        if (isNoAi()) {
            super.tick();
            return;
        }

        if (isFreezeConverting()) {
            conversionTime -= 1;
            if (conversionTime < 0) {
                doFreezeConversion();
            }

        } else if (isInPowderSnow) {
            inPowderSnowTime += 1;

            if (inPowderSnowTime >= 140) {
                startFreezeConversion(300);
            }

        } else {
            inPowderSnowTime = -1;
        }

        super.tick();
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putInt(TAG_LICH_CONVERSION, isFreezeConverting() ? conversionTime : -1);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains(TAG_LICH_CONVERSION, 99) && tag.getInt(TAG_LICH_CONVERSION) > -1) {
            startFreezeConversion(tag.getInt("StrayConversionTime"));
        }
    }

    private void startFreezeConversion(int ticks) {
        conversionTime = ticks;
        entityData.set(LICH_CONVERSION, ticks);
    }

    public void doFreezeConversion() {
        convertTo(EntityType.STRAY, true);
        if (!isSilent()) {
            level.levelEvent(null, LevelEvent.SOUND_SKELETON_TO_STRAY, blockPosition(), 0);
        }
    }

    public boolean canFreeze() {
        return true;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected void dropCustomDeathLoot(DamageSource source, int i, boolean bl) {
        super.dropCustomDeathLoot(source, i, bl);
        final var entity = source.getEntity();

        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                spawnAtLocation(Items.SKELETON_SKULL);
            }
        }
    }

    @Override
    protected double getMaximumManaForDifficulty(@NotNull DifficultyInstance difficulty) {
        return switch (difficulty.getDifficulty()) {
            case PEACEFUL -> 100;
            case EASY -> 100;
            case NORMAL -> 150;
            case HARD -> 250;
        };
    }

    @Override
    protected double getManaRegenerationPerTickForDifficulty(@NotNull DifficultyInstance difficulty) {
        return switch (difficulty.getDifficulty()) {
            case PEACEFUL -> 1;
            case EASY -> 1;
            case NORMAL -> 2;
            case HARD -> 3;
        };
    }

    @Override
    protected double getChanneledManaForDifficulty(@NotNull DifficultyInstance difficulty) {
        return switch (difficulty.getDifficulty()) {
            case PEACEFUL -> 1;
            case EASY -> 1;
            case NORMAL -> 2;
            case HARD -> 3;
        };
    }

    @Override
    protected @NotNull List<@NotNull Spell> getSpellRepertoireForDifficulty(@NotNull DifficultyInstance difficulty) {
        return Lists.newArrayList();
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawn, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        data = super.finalizeSpawn(level, difficulty, spawn, data, tag);
        final var random = level.getRandom();

        populateDefaultEquipmentSlots(random, difficulty);
        populateDefaultEquipmentEnchantments(random, difficulty);
        setCanPickUpLoot(true);

        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            LocalDate localDate = LocalDate.now();
            int i = localDate.get(ChronoField.DAY_OF_MONTH);
            int j = localDate.get(ChronoField.MONTH_OF_YEAR);

            if (j == 10 && i == 31 && random.nextFloat() < 0.5f) {
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        return data;
    }

    @Override
    public @NotNull Vec3 getSpellCastingPosition() {
        return getEyePosition();
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 1.74F;
    }

    public double getMyRidingOffset() {
        return -0.6;
    }
}
