package mikolmisol.spellcraft.entities.impl;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public final class ManaSpark extends Entity {
    public static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(ManaSpark.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> GROWING = SynchedEntityData.defineId(ManaSpark.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(ManaSpark.class, EntityDataSerializers.INT);

    public ManaSpark(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(SIZE, 1f);
        entityData.define(GROWING, false);
        entityData.define(TIMER, 0);
    }

    public float getSize() {
        return entityData.get(SIZE);
    }

    public void incrementSize() {
        entityData.set(SIZE, getSize() * 1.02f);
    }

    public void decrementSize() {
        entityData.set(SIZE, getSize() * 0.95f);
    }

    public boolean isGrowing() {
        return entityData.get(GROWING);
    }

    public void stopGrowing() {
        entityData.set(GROWING, false);
    }

    public int getTimer() {
        return entityData.get(TIMER);
    }

    public void incrementTimer() {
        final var timer = entityData.get(TIMER);

        if (getTimer() < 100) {
            entityData.set(TIMER, timer + 1);
        } else {
            discard();
        }
    }

    @Override
    public boolean isAlwaysTicking() {
        return true;
    }

    @Override
    public void tick() {
        incrementTimer();

        if (isGrowing()) {
            if (getSize() < 1000f) {
                incrementSize();
            } else {
                stopGrowing();
            }
        } else {
            if (getSize() > 0.01f) {
                decrementSize();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
