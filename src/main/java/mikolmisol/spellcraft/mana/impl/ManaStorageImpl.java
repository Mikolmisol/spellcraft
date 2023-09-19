package mikolmisol.spellcraft.mana.impl;

import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.util.ManaUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ManaStorageImpl implements ManaStorage {

    private double capacity;

    private double amount;

    private @Nullable OnManaChangedListener onManaChanged;

    public ManaStorageImpl(double capacity, double amount) throws IllegalArgumentException {
        if (amount > capacity) {
            throw new IllegalArgumentException("'amount' must not be greater than 'capacity'.");
        }

        this.capacity = capacity;
        this.amount = amount;
    }

    public ManaStorageImpl(double capacity) throws IllegalArgumentException {
        this(capacity, 0);
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

    @Override
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public double extract(double maxAmount, @NotNull TransactionContext transaction) throws IllegalArgumentException {
        ManaUtil.assertGreaterThanZero(maxAmount);

        if (amount > maxAmount) {

            transaction.addCloseCallback((closingTransaction, result) -> {
                if (result.wasCommitted()) {
                    amount -= maxAmount;

                    if (onManaChanged != null) {
                        onManaChanged.onManaChanged(amount);
                    }
                }
            });

            return maxAmount;
        }

        transaction.addCloseCallback((closingTransaction, result) -> {
            if (result.wasCommitted()) {
                amount = 0;

                if (onManaChanged != null) {
                    onManaChanged.onManaChanged(amount);
                }
            }
        });

        return amount;
    }

    @Override
    public double insert(double maxAmount, @NotNull TransactionContext transaction) throws IllegalArgumentException {
        ManaUtil.assertGreaterThanZero(maxAmount);

        if (amount + maxAmount <= capacity) {

            transaction.addCloseCallback((closingTransaction, result) -> {
                if (result.wasCommitted()) {
                    amount += maxAmount;

                    if (onManaChanged != null) {
                        onManaChanged.onManaChanged(amount);
                    }
                }
            });

            return maxAmount;
        }

        transaction.addCloseCallback((closingTransaction, result) -> {
            if (result.wasCommitted()) {
                amount = capacity;

                if (onManaChanged != null) {
                    onManaChanged.onManaChanged(amount);
                }
            }
        });

        return capacity - amount;
    }

    @Override
    public void setOnManaChangedListener(@NotNull OnManaChangedListener onManaChanged) {
        this.onManaChanged = onManaChanged;
    }
}
