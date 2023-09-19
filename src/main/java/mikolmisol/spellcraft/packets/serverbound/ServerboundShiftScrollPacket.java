package mikolmisol.spellcraft.packets.serverbound;

import mikolmisol.spellcraft.Spellcraft;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ServerboundShiftScrollPacket(int integralAccumulatedScroll) implements FabricPacket {
    public static final PacketType<ServerboundShiftScrollPacket> TYPE = PacketType.create(new ResourceLocation(Spellcraft.MOD_ID, "serverbound_shift_scroll"), ServerboundShiftScrollPacket::new);

    public ServerboundShiftScrollPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(integralAccumulatedScroll);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
