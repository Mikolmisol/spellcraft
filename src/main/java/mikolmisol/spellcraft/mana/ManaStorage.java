package mikolmisol.spellcraft.mana;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.mana.impl.CreativeManaStorageImpl;
import mikolmisol.spellcraft.mana.impl.ManaStorageImpl;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ManaStorage {
    BlockApiLookup<ManaStorage, @Nullable Direction> SIDED =
            BlockApiLookup.get(new ResourceLocation(Spellcraft.MOD_ID, "sided_mana_storage"), ManaStorage.class, Direction.class);

    String AMOUNT = "SpellcraftManaAmount";
    String CAPACITY = "SpellcraftManaCapacity";

    static ManaStorage fromTag(CompoundTag tag) {
        final var amount = tag.getDouble(AMOUNT);
        final var capacity = tag.getDouble(CAPACITY);

        return new ManaStorageImpl(amount, capacity);
    }

    static ManaStorage of(double capacity, double amount) {
        return new ManaStorageImpl(capacity, amount);
    }

    static ManaStorage of(double capacity) {
        return new ManaStorageImpl(capacity);
    }

    static ManaStorage creative() {
        return CreativeManaStorageImpl.INSTANCE;
    }

    double getAmount();

    void setAmount(double amount);

    double getCapacity();

    void setCapacity(double capacity);

    double extract(double maxAmount, @NotNull TransactionContext transaction) throws IllegalArgumentException;

    double insert(double maxAmount, @NotNull TransactionContext transaction) throws IllegalArgumentException;

    default void toTag(@NotNull CompoundTag tag) {
        tag.putDouble(AMOUNT, getAmount());
        tag.putDouble(CAPACITY, getCapacity());
    }

    void setOnManaChangedListener(@NotNull OnManaChangedListener onManaChanged);

    @FunctionalInterface
    interface OnManaChangedListener {
        void onManaChanged(double amount);
    }
}
