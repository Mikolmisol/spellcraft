package mikolmisol.spellcraft.entities.impl;

import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.entities.entity_data.SpellcraftEntityDataSerializers;
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
import net.minecraft.world.phys.Vec3;

import java.util.random.RandomGenerator;

public final class SpellBolt extends Entity {
    public static final EntityDataAccessor<Vec3> ORIGIN = SynchedEntityData.defineId(SpellBolt.class, SpellcraftEntityDataSerializers.VEC3);

    public static final EntityDataAccessor<Vec3> END = SynchedEntityData.defineId(SpellBolt.class, SpellcraftEntityDataSerializers.VEC3);

    public static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(SpellBolt.class, EntityDataSerializers.INT);

    private int timer;

    private int seed = RandomGenerator.getDefault().nextInt();

    public SpellBolt(EntityType<?> type, Level level) {
        super(type, level);
        this.seed = level.random.nextInt();
    }

    public static SpellBolt create(Level level) {
        return new SpellBolt(SpellcraftEntityTypes.SPELL_BOLT, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ORIGIN, null);
        entityData.define(END, null);
        entityData.define(COLOR, 0);
    }

    @Override
    public boolean isAlwaysTicking() {
        return true;
    }

    @Override
    public void tick() {
        timer += 1;

        if (timer > 10) {
            discard();
        }
    }

    public int getColor() {
        return entityData.get(COLOR);
    }

    public int getTimer() {
        return timer;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public Vec3 getOrigin() {
        return entityData.get(ORIGIN);
    }

    public Vec3 getEnd() {
        return entityData.get(END);
    }

    public void strike(Vec3 origin, Vec3 end, int color) {
        entityData.set(ORIGIN, origin);
        entityData.set(END, end);
        entityData.set(COLOR, color);
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
