package mikolmisol.spellcraft.items.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.items.ScrollableItem;
import mikolmisol.spellcraft.items.SelectableItem;
import mikolmisol.spellcraft.items.SpellContainingItem;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.util.TextUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class SpellBookItem extends SpellCastingItem implements ScrollableItem, SelectableItem {

    private static final Component NAME;

    private static final String TAG_FLIPPING_RIGHT = "Spellcraft Flipping Page Right";

    private static final String TAG_FLIPPING_LEFT = "Spellcraft Flipping Page Left";

    private static final String TAG_OPENING = "Spellcraft Opening";

    private static final String TAG_CLOSING = "Spellcraft Closing";

    private static final String TAG_SELECTED_SPELL_INDEX = "Spellcraft Selected Spell Index";

    public SpellBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable Spell getSpell(@NotNull ItemStack item) {
        if (true) {
            return super.getSpell(item);
        }

        final var spellContainingItemStack = getSpellContainingItems(item).get(getSelectedSpellIndex(item));
        if (spellContainingItemStack.getItem() instanceof SpellContainingItem spellContainingItem) {
            return spellContainingItem.getSpell(spellContainingItemStack);
        }
        return null;
    }

    @Override
    public void putSpell(@NotNull ItemStack item, @NotNull Spell spell) {
        final var spellContainingItems = getSpellContainingItems(item);
        final var spellContainingItemStack = spellContainingItems.get(getSelectedSpellIndex(item));
        if (spellContainingItemStack.getItem() instanceof SpellContainingItem spellContainingItem) {
            spellContainingItem.putSpell(spellContainingItemStack, spell);
        }
        ContainerHelper.saveAllItems(item.getOrCreateTag(), NonNullList.of(ItemStack.EMPTY, spellContainingItems.toArray(new ItemStack[0])));
    }

    public List<ItemStack> getSpellContainingItems(ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return Collections.emptyList();
        }

        final var spellContainingItems = NonNullList.withSize(9, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, spellContainingItems);

        return spellContainingItems;
    }

    @Override
    public void onShiftScrollWhileHolding(Player player, ItemStack item, int scrollDelta) {
        final var delta = (int) Math.signum(scrollDelta);
        final var selectedSpellIndex = getSelectedSpellIndex(item);
        var newSelectedSpellIndex = selectedSpellIndex + delta;

        final var spellContainingItems = getSpellContainingItems(item);
        if (newSelectedSpellIndex >= spellContainingItems.size()) {
            newSelectedSpellIndex = 0;
        } else if (newSelectedSpellIndex < 0) {
            newSelectedSpellIndex = Math.max(spellContainingItems.size() - 1, 0);
        }

        if (selectedSpellIndex != newSelectedSpellIndex) {
            if (delta > 0) {
                flipRight(item);
            } else {
                flipLeft(item);
            }
            player.playSound(SoundEvents.BOOK_PAGE_TURN, 10f, 1f);
        }

        setSelectedSpellIndex(item, newSelectedSpellIndex);
    }

    public int getSelectedSpellIndex(ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return 0;
        }

        return tag.getInt(TAG_SELECTED_SPELL_INDEX);
    }

    public void setSelectedSpellIndex(ItemStack item, int index) {
        item.getOrCreateTag().putInt(TAG_SELECTED_SPELL_INDEX, index);
    }

    public boolean isOpening(ItemStack item) {
        final var tag = item.getTag();

        if (tag != null) {
            return tag.getBoolean(TAG_OPENING);
        }

        return false;
    }

    public void open(ItemStack item) {
        item.getOrCreateTag().putBoolean(TAG_OPENING, true);
    }

    public void stopOpening(ItemStack item) {
        item.getOrCreateTag().putBoolean(TAG_OPENING, false);
    }

    public boolean isFlippingRight(ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return false;
        }

        return tag.getBoolean(TAG_FLIPPING_RIGHT);
    }

    public void flipRight(ItemStack item) {
        item.getOrCreateTag().putBoolean(TAG_FLIPPING_RIGHT, true);
    }

    public void stopFlippingRight(ItemStack item) {
        final var tag = item.getTag();

        if (tag != null) {
            tag.putBoolean(TAG_FLIPPING_RIGHT, false);
        }
    }

    public void flipLeft(ItemStack item) {
        item.getOrCreateTag().putBoolean(TAG_FLIPPING_LEFT, true);
    }

    public boolean isFlippingLeft(ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return false;
        }

        return tag.getBoolean(TAG_FLIPPING_LEFT);
    }

    public void stopFlippingLeft(ItemStack item) {
        final var tag = item.getTag();

        if (tag != null) {
            tag.putBoolean(TAG_FLIPPING_LEFT, false);
        }
    }

    @Override
    public void onSelected(@NotNull ItemStack item) {
        open(item);
    }

    static {
        NAME = TextUtil.translate(
                String.format("gui.%s.spell_book", Spellcraft.MOD_ID),
                "Spell Book"
        );
    }
}
