package mikolmisol.spellcraft.packets.clientbound;

import mikolmisol.spellcraft.Spellcraft;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ClientboundUpdateManaStoragePacket(double amount, double capacity) implements FabricPacket {
    public static final PacketType<ClientboundUpdateManaStoragePacket> TYPE = PacketType.create(new ResourceLocation(Spellcraft.MOD_ID, "clientbound_update_mana_storage"), ClientboundUpdateManaStoragePacket::new);

    public ClientboundUpdateManaStoragePacket(@NotNull FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(amount);
        buf.writeDouble(capacity);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
