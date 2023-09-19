package mikolmisol.spellcraft.packets.serverbound;

import mikolmisol.spellcraft.Spellcraft;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ServerboundRaycastEntityResultPacket(int entityIdentifier) implements FabricPacket {

    public static final PacketType<ServerboundRaycastEntityResultPacket> TYPE = PacketType.create(new ResourceLocation(Spellcraft.MOD_ID, "serverbound_raycast_entity_result"), ServerboundRaycastEntityResultPacket::new);

    public ServerboundRaycastEntityResultPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityIdentifier);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
