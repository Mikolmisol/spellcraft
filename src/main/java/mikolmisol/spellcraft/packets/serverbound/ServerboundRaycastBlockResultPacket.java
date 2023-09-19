package mikolmisol.spellcraft.packets.serverbound;

import mikolmisol.spellcraft.Spellcraft;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ServerboundRaycastBlockResultPacket(@NotNull BlockPos position,
                                                  @NotNull Direction direction) implements FabricPacket {

    public static final PacketType<ServerboundRaycastBlockResultPacket> TYPE = PacketType.create(new ResourceLocation(Spellcraft.MOD_ID, "serverbound_raycast_block_result"), ServerboundRaycastBlockResultPacket::new);

    public ServerboundRaycastBlockResultPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), Direction.from3DDataValue(buf.readInt()));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(position);
        buf.writeInt(direction.get3DDataValue());
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
