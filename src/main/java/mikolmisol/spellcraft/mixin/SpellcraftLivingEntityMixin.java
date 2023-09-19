package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.accessors.ArcaneShieldAccessor;
import mikolmisol.spellcraft.accessors.ThrallAccessor;
import mikolmisol.spellcraft.entities.entity_data.SpellcraftEntityDataSerializers;
import mikolmisol.spellcraft.items.OnStartUseTick;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class SpellcraftLivingEntityMixin extends Entity implements ArcaneShieldAccessor {

    @Unique
    private static final double MAXIMUM_ARCANE_BARRIER_STRENGTH = 10;

    @Unique
    private static final String TAG_ARCANE_BARRIER = "SpellcraftArcaneBarrier";

    @Unique
    private static final EntityDataAccessor<Double> ARCANE_BARRIER = SynchedEntityData.defineId(LivingEntity.class, SpellcraftEntityDataSerializers.DOUBLE);

    public SpellcraftLivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean canAttack(LivingEntity livingEntity);

    @Shadow
    public abstract ItemStack getItemInHand(InteractionHand interactionHand);

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "canAttack",
                    args = LivingEntity.class,
                    ret = boolean.class
            ),
            cancellable = true
    )
    public boolean spellcraft_onCanAttack(LivingEntity living, CallbackInfoReturnable<Boolean> callback) {
        if (this instanceof ThrallAccessor thrall) {
            final var master = thrall.spellcraft_getMaster();
            if (living != null && master != null /* this is actually necessary */) {
                if (living.equals(master)) {
                    callback.setReturnValue(false);
                    callback.cancel();
                } else if (living instanceof ThrallAccessor alsoThrall) {
                    final var otherMaster = alsoThrall.spellcraft_getMaster();

                    if (otherMaster != null) {
                        callback.setReturnValue(canAttack(otherMaster));
                        callback.cancel();
                    }
                } else {
                    callback.setReturnValue(master.canAttack(living));
                    callback.cancel();
                }
            }
        }

        return false;
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "startUsingItem",
                    args = InteractionHand.class
            )
    )
    void onStartUsingItem(InteractionHand hand, CallbackInfo callback) {
        final var item = getItemInHand(hand);

        if (item.getItem() instanceof OnStartUseTick onStartUseTick) {
            onStartUseTick.onStartUseTick(JavaUtil.cast(this), hand, item);
        }
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "defineSynchedData"
            )
    )
    protected void onDefineSynchedData(CallbackInfo callback) {
        entityData.define(ARCANE_BARRIER, 0.0);
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "readAdditionalSaveData",
                    args = CompoundTag.class
            )
    )
    public void onReadAdditionalSaveData(@NotNull CompoundTag tag, CallbackInfo callback) {
        spellcraft_setArcaneShieldStrength(tag.getDouble(TAG_ARCANE_BARRIER));
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "addAdditionalSaveData",
                    args = CompoundTag.class
            )
    )
    public void onAddAdditionalSaveData(@NotNull CompoundTag tag, CallbackInfo callback) {
        tag.putDouble(TAG_ARCANE_BARRIER, spellcraft_getArcaneShieldStrength());
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "hurt",
                    args = {DamageSource.class, float.class},
                    ret = boolean.class
            ),
            cancellable = true
    )
    public boolean onHurt(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> callback) {
        final var arcaneBarrierStrength = spellcraft_getArcaneShieldStrength();

        if (arcaneBarrierStrength > 0
                && !damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)
                && !damageSource.is(DamageTypes.STARVE)
                && !damageSource.is(DamageTypes.OUTSIDE_BORDER)
        ) {
            if (arcaneBarrierStrength > damage) {
                if (invulnerableTime <= 10 || damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
                    invulnerableTime = 20;

                    ArcaneShieldAccessor.onArcaneShieldDamage(JavaUtil.cast(this), damageSource);
                    spellcraft_setArcaneShieldStrength(arcaneBarrierStrength - damage);
                    callback.setReturnValue(false);
                }
            } else {
                ArcaneShieldAccessor.onArcaneShieldBreak(JavaUtil.cast(this), damageSource);
                spellcraft_setArcaneShieldStrength(0);
                callback.setReturnValue(hurt(damageSource, (float) (damage - arcaneBarrierStrength)));
            }
        }

        return false;
    }

    @Override
    public void spellcraft_setArcaneShieldStrength(double strength) {
        entityData.set(
                ARCANE_BARRIER,
                Math.min(
                        spellcraft_getMaximumArcaneShieldStrength(),
                        Math.max(
                                0,
                                strength
                        )
                )
        );
    }

    @Override
    public double spellcraft_getArcaneShieldStrength() {
        return entityData.get(ARCANE_BARRIER);
    }

    @Override
    public double spellcraft_getMaximumArcaneShieldStrength() {
        return 20;
    }
}
