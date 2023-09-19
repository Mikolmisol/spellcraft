package mikolmisol.spellcraft.block_entities.container;

import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SimpleImplementedContainer extends Container {

    @NotNull List<ItemStack> getItems();

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (final var item : getItems()) {
            if (item.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    default ItemStack getItem(int i) {
        return getItems().get(i);
    }

    @Override
    default ItemStack removeItem(int i, int j) {
        final var result = ContainerHelper.removeItem(getItems(), i, j);
        setChanged();
        return result;
    }

    @Override
    default ItemStack removeItemNoUpdate(int i) {
        final var result = ContainerHelper.takeItem(getItems(), i);
        setChanged();
        return result;
    }

    @Override
    default void setItem(int i, ItemStack itemStack) {
        getItems().set(i, itemStack);
        setChanged();
    }

    @Override
    default void clearContent() {
        getItems().clear();
        setChanged();
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }
}
