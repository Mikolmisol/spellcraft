package mikolmisol.spellcraft.packets.clientbound;

import mikolmisol.spellcraft.Spellcraft;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundRequestRaycastPacket(double reach) implements FabricPacket {
    public static final PacketType<ClientboundRequestRaycastPacket> TYPE = PacketType.create(new ResourceLocation(Spellcraft.MOD_ID, "clientbound_request_raycast"), ClientboundRequestRaycastPacket::new);

    public ClientboundRequestRaycastPacket(FriendlyByteBuf buf) {
        this(buf.readDouble());
    }


    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(reach);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
