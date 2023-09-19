package mikolmisol.spellcraft.packets.clientbound;

import mikolmisol.spellcraft.Spellcraft;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundUpdateSpellCastingProgressPacket(double progress, double total) implements FabricPacket {
    public static final PacketType<ClientboundUpdateSpellCastingProgressPacket> TYPE = PacketType.create(new ResourceLocation(Spellcraft.MOD_ID, "clientbound_update_spell_casting_progress"), ClientboundUpdateSpellCastingProgressPacket::new);

    public ClientboundUpdateSpellCastingProgressPacket(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(progress);
        buf.writeDouble(total);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
