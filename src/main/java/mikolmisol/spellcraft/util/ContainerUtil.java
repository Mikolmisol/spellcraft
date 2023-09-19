package mikolmisol.spellcraft.util;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.items.ManaContainingItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class ContainerUtil {
    public CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
        ListTag list = new ListTag();

        for (int i = 0; i < items.size(); ++i) {
            final var itemStack = items.get(i);

            final var itemStackTag = new CompoundTag();
            itemStackTag.putByte("Slot", (byte) i);

            if (itemStack.isEmpty()) {
                itemStackTag.putString("id", "empty");
            } else {
                itemStack.save(itemStackTag);
            }

            list.add(itemStackTag);
        }

        if (!list.isEmpty()) {
            tag.put("Items", list);
        }

        return tag;
    }

    public void loadAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
        final var listTag = tag.getList("Items", 10);

        for (var i = 0; i < listTag.size(); ++i) {
            final var itemStackTag = listTag.getCompound(i);
            final var j = itemStackTag.getByte("Slot") & 255;

            if (j >= 0 && j < items.size()) {
                if ("empty".equals(itemStackTag.getString("id"))) {
                    items.set(j, ItemStack.EMPTY);
                } else {
                    items.set(j, ItemStack.of(itemStackTag));
                }
            }
        }
    }

    public List<ItemStack> getAllManaContainingItems(@NotNull Player player) {
        final var equippedTrinkets = TrinketsApi.getTrinketComponent(player)
                .map(TrinketComponent::getAllEquipped)
                .map(list -> list.stream().map(Tuple::getB))
                .orElse(Stream.empty())
                .toList();

        final var inventory = player.getInventory();

        final var items = new ArrayList<ItemStack>(
                inventory.items.size() + inventory.armor.size() + inventory.offhand.size() + equippedTrinkets.size()
        );

        items.addAll(inventory.items);
        items.addAll(inventory.armor);
        items.addAll(inventory.offhand);
        items.addAll(equippedTrinkets);

        return items.stream().filter(item -> item.getItem() instanceof ManaContainingItem).toList();
    }
}
