package mikolmisol.spellcraft.block_entities.crystal;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class ManaCrystalBlockEntity extends BlockEntity {
    private static final String TAG_SIZE = "Size";

    private double size;

    public ManaCrystalBlockEntity(BlockPos position, BlockState block) {
        super(SpellcraftBlockEntityTypes.MANA_CRYSTAL, position, block);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
        setChanged();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        final var tag = super.getUpdateTag();
        tag.putDouble(TAG_SIZE, size);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putDouble(TAG_SIZE, size);
    }

    @Override
    public void load(CompoundTag tag) {
        size = tag.getDouble(TAG_SIZE);
    }
}
