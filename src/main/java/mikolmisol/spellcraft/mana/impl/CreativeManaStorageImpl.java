package mikolmisol.spellcraft.mana.impl;

import mikolmisol.spellcraft.mana.ManaStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

public final class CreativeManaStorageImpl implements ManaStorage {
    public static final CreativeManaStorageImpl INSTANCE = new CreativeManaStorageImpl();

    private CreativeManaStorageImpl() {
    }

    @Override
    public double getAmount() {
        return Double.MAX_VALUE;
    }

    @Override
    public void setAmount(double amount) {

    }

    @Override
    public double getCapacity() {
        return Double.MAX_VALUE;
    }

    @Override
    public void setCapacity(double capacity) {

    }

    @Override
    public double extract(double maxAmount, @NotNull TransactionContext transaction) throws IllegalArgumentException {
        return maxAmount;
    }

    @Override
    public double insert(double maxAmount, @NotNull TransactionContext transaction) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public void setOnManaChangedListener(@NotNull OnManaChangedListener onManaChanged) {

    }
}
