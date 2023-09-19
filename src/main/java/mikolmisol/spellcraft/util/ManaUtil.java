package mikolmisol.spellcraft.util;

import com.google.common.math.DoubleMath;
import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.items.ManaContainingItem;
import mikolmisol.spellcraft.mana.ManaStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UtilityClass
public class ManaUtil {

    private final double BASE_MANA_REGENERATION = 0.25;

    public void assertGreaterThanZero(double maxAmount) throws IllegalArgumentException {
        if (maxAmount == 0) {
            throw new IllegalArgumentException("'maxAmount' must be greater than 0.");
        } else if (maxAmount < 0) {
            throw new IllegalArgumentException("'maxAmount' must be greater than 0.");
        }
    }

    public boolean equals(double amount1, double amount2) {
        return DoubleMath.fuzzyEquals(amount1, amount2, 0.00001);
    }

    public boolean tryConsumeMana(double amount, @NotNull ManaStorage mana) {
        try (final var transaction = Transaction.openOuter()) {

            final var extracted = mana.extract(amount, transaction);

            if (equals(amount, extracted)) {
                transaction.commit();
                return true;
            }
        } catch (IllegalStateException ignored) {
        }

        return false;
    }

    public void insertManaAndDiscardOverflow(double amount, @NotNull ManaStorage mana) {
        try (final var transaction = Transaction.openOuter()) {

            mana.insert(amount, transaction);
            transaction.commit();
        } catch (IllegalStateException ignored) {
        }
    }

    public void regeneratePlayerMana(@NotNull Player player, @NotNull ManaStorage mana) {
        final var armor = player.getArmorValue();
        final var armorManaRegenPenalty = Math.pow(0.9, armor);
        final var manaRegenerationMultiplier = armorManaRegenPenalty;

        insertManaAndDiscardOverflow(BASE_MANA_REGENERATION * manaRegenerationMultiplier, mana);
    }

    public void getManaFromManaProvidingItems(@NotNull Player player, @NotNull ManaStorage mana, @NotNull List<ItemStack> items, double manaTransferRatePerTick) {
        for (final var item : items) {
            final var manaContainingItem = (ManaContainingItem) item.getItem();

            if (!manaContainingItem.canOwnerExtractMana(player, item)) {
                continue;
            }

            final var itemMana = manaContainingItem.getManaStorage(item);

            try (final var transaction = Transaction.openOuter()) {
                final var extracted = itemMana.extract(manaTransferRatePerTick, transaction);

                if (extracted <= 0) {
                    continue;
                }

                final var inserted = mana.insert(extracted, transaction);

                if (inserted > 0) {
                    transaction.commit();
                    itemMana.toTag(item.getOrCreateTag());
                }
            }
        }
    }

    public void chargeManaContainingItems(@NotNull Player player, @NotNull ManaStorage mana, @NotNull List<ItemStack> items, double manaTransferRatePerTick) {
        for (final var item : items) {
            final var manaContainingItem = (ManaContainingItem) item.getItem();

            if (!manaContainingItem.canOwnerInsertMana(player, item)) {
                continue;
            }

            final var itemMana = manaContainingItem.getManaStorage(item);

            if (itemMana == null) {
                continue;
            }

            itemMana.toTag(item.getOrCreateTag());
        }
    }
}
