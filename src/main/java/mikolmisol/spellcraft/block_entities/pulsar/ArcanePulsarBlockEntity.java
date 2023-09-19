package mikolmisol.spellcraft.block_entities.pulsar;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.container.SimpleImplementedContainer;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import mikolmisol.spellcraft.blocks.pulsar.ArcanePulsarBlock;
import mikolmisol.spellcraft.items.SpellContainingItem;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ArcanePulsarBlockEntity extends BlockEntity implements SimpleImplementedContainer, Caster {
    public static int SPELL_SLOT = 0;
    public static int FILTER_SLOT = 1;
    public static int SLOT_COUNT = 2;
    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private ManaStorage mana = ManaStorage.creative();
    private int timer;

    public ArcanePulsarBlockEntity(BlockPos position, BlockState block) {
        super(SpellcraftBlockEntityTypes.ARCANE_PULSAR, position, block);
    }

    public static void clientTick(Level level, BlockPos position, BlockState state, ArcanePulsarBlockEntity lantern) {
        if (!(state.is(SpellcraftBlocks.ARCANE_LANTERN) && state.getValue(ArcanePulsarBlock.LIT))) {
            lantern.timer = 0;
            return;
        }

        lantern.timer += 1;

        if (lantern.timer > ArcanePulsarBlock.LIT_DURATION_TICKS) {
            lantern.timer = 0;
        }
    }

    public void emanateSpell(Level level) {
        final var item = items.get(0);

        if (!(item.getItem() instanceof SpellContainingItem spellContainingItem)) {
            return;
        }

        final var spell = spellContainingItem.getSpell(item);

        if (spell == null) {
            return;
        }

        spell.cast(this, level);
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        mana.toTag(tag);
        ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public void load(CompoundTag tag) {
        mana = ManaStorage.fromTag(tag);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    public @NotNull ManaStorage getManaStorage() {
        return mana;
    }

    @Override
    public @NotNull Vec3 getSpellCastingPosition() {
        return Vec3.atCenterOf(getBlockPos());
    }

    @Override
    public void spellcraft_startCasting(@NotNull Spell spell, @NotNull Level level) {

    }

    private void startCastTick(@NotNull Spell spell, @NotNull Level level) {

    }

    private void castTick(@NotNull Level level) {

    }

    @Override
    public boolean spellcraft_isCasting() {
        return false;
    }

    @Override
    public void spellcraft_stopCasting() {

    }

    @Override
    public void spellcraft_removeSpellCastEventReference() {

    }

    public int getTimer() {
        return timer;
    }
}
