package mikolmisol.spellcraft.block_entities.pedestal;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.container.SimpleImplementedContainer;
import mikolmisol.spellcraft.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArcanePedestalBlockEntity extends SpellcraftBlockEntity implements SimpleImplementedContainer {

    private final NonNullList<ItemStack> item = NonNullList.withSize(1, ItemStack.EMPTY);

    public ArcanePedestalBlockEntity(BlockPos pos, BlockState state) {
        super(SpellcraftBlockEntityTypes.ARCANE_PEDESTAL, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerUtil.saveAllItems(tag, item);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerUtil.loadAllItems(tag, item);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        sendUpdateToClients();
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return item;
    }
}
