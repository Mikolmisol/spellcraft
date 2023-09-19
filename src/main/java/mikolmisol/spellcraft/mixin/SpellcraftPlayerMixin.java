package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.accessors.PlayerManaAccessor;
import mikolmisol.spellcraft.entities.impl.ArcaneBroom;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.packets.clientbound.ClientboundUpdateManaStoragePacket;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import mikolmisol.spellcraft.util.ContainerUtil;
import mikolmisol.spellcraft.util.JavaUtil;
import mikolmisol.spellcraft.util.ManaUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class SpellcraftPlayerMixin extends LivingEntity implements Caster, PlayerManaAccessor {

    @Unique
    private ManaStorage mana = ManaStorage.of(100);

    @Unique
    private @Nullable SpellCastEvent spellCastEvent;

    @Unique
    private double spellCastingProgress;

    @Unique
    private double spellCastingTotal;

    private SpellcraftPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public double spellcraft_getChanneledManaPerTick() {
        final var armor = getArmorValue();
        return 1.0 / armor;
    }

    @Override
    public double spellcraft_getSpellCostMultiplier() {
        final var armor = getArmorValue();
        return Math.pow(1.1, armor);
    }

    @Override
    public void spellcraft_setSpellCastingProgress(double progress) {
        this.spellCastingProgress = progress;
    }

    @Override
    public void spellcraft_setSpellCastingTotal(double total) {
        this.spellCastingTotal = total;
    }

    @Override
    public void spellcraft_startCasting(@NotNull Spell spell, @NotNull Level level) {
        spellCastEvent = spell.getSpellCastEventConstructor().create(spell, this, level);
    }

    @Override
    public boolean spellcraft_isCasting() {
        return spellCastEvent != null;
    }

    @Override
    public void spellcraft_stopCasting() {
        if (spellCastEvent != null) {
            spellCastEvent.failSpellCast();
        }
    }

    @Override
    public void spellcraft_removeSpellCastEventReference() {
        spellCastEvent = null;
    }

    @Shadow
    public abstract boolean hurt(DamageSource damageSource, float f);

    @Shadow
    protected abstract boolean isAboveGround();

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void spellcraft_onTick(CallbackInfo info) {
        final var player = JavaUtil.<Player>cast(this);

        if (!(player.isDeadOrDying()) && player instanceof ServerPlayer serverPlayer) {
            final var items = ContainerUtil.getAllManaContainingItems(player);
            final var manaTransferRatePerTick = spellcraft_getChanneledManaPerTick();

            ManaUtil.chargeManaContainingItems(player, mana, items, manaTransferRatePerTick);
            ManaUtil.getManaFromManaProvidingItems(player, mana, items, manaTransferRatePerTick);
            ManaUtil.regeneratePlayerMana(player, mana);

            ServerPlayNetworking.send(
                    serverPlayer,
                    new ClientboundUpdateManaStoragePacket(
                            mana.getAmount(),
                            mana.getCapacity()
                    )
            );
        }
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "readAdditionalSaveData",
                    args = CompoundTag.class
            )
    )
    public void spellcraft_onReadAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
        mana = ManaStorage.fromTag(tag);
    }

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "addAdditionalSaveData",
                    args = CompoundTag.class
            )
    )
    public void spellcraft_onAddAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
        mana.toTag(tag);
    }

    @Override
    public @NotNull ManaStorage getManaStorage() {
        return mana;
    }

    @Override
    public @NotNull Vec3 getSpellCastingPosition() {
        return getEyePosition();
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "getDestroySpeed",
                    args = BlockState.class,
                    ret = float.class
            ),
            cancellable = true
    )
    public float onGetDestroySpeed(BlockState block, CallbackInfoReturnable<Float> callback) {
        if (isAboveGround() && getVehicle() instanceof ArcaneBroom) {
            callback.setReturnValue(callback.getReturnValueF() * 2);
        }

        callback.setReturnValue(callback.getReturnValueF());
        return 0;
    }

    @Override
    public void spellcraft_setManaAmount(double amount) {
        mana.setAmount(amount);
    }

    @Override
    public void spellcraft_setManaCapacity(double capacity) {
        mana.setCapacity(capacity);
    }
}