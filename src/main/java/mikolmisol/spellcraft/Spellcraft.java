package mikolmisol.spellcraft;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.blocks.SpellcraftBlocks;
import mikolmisol.spellcraft.items.SpellcraftItems;
import mikolmisol.spellcraft.menus.SpellcraftMenuTypes;
import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import mikolmisol.spellcraft.packets.clientbound.ClientboundRequestRaycastPacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundRaycastBlockResultPacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundRaycastEntityResultPacket;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.effects.SpellcraftEffects;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifierHandlers;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifiers;
import mikolmisol.spellcraft.spells.shapes.SpellcraftShapes;
import mikolmisol.spellcraft.spells.targets.Target;
import mikolmisol.spellcraft.spells.targets.Targets;
import mikolmisol.spellcraft.util.GeomUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Spellcraft implements ModInitializer {
    public static final String MOD_ID = "spellcraft";
    public static final String VERSION = getVersion(1, 0, 0);
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Map<String, List<Consumer<Targets>>> RAYCAST_CONSUMERS = Maps.newConcurrentMap();

    private static String getVersion(int major, int minor, int patch) {
        return major + "." + minor + "." + patch;
    }

    private static void registerPacketReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(ServerboundRaycastBlockResultPacket.TYPE, Spellcraft::receiveRaycastBlockResultPacket);
        ServerPlayNetworking.registerGlobalReceiver(ServerboundRaycastEntityResultPacket.TYPE, Spellcraft::receiveRaycastEntityResultPacket);
    }

    public static void sendRequestRaycastPacket(@NotNull ServerPlayer player, double reach, @NotNull Consumer<Targets> then) {
        ServerPlayNetworking.send(player, new ClientboundRequestRaycastPacket(reach));
        RAYCAST_CONSUMERS.computeIfAbsent(player.getStringUUID(), uuid -> Lists.newArrayList()).add(then);
    }

    private static void receiveRaycastBlockResultPacket(ServerboundRaycastBlockResultPacket packet, ServerPlayer player, PacketSender responseSender) {
        if (!GeomUtil.isBlockTargetValid(player, packet.position())) {
            return;
        }

        final var targets = Targets.of(Target.ofBlock(packet.position(), packet.direction()));

        notifyRaycastResultListeners(player, targets);
    }

    private static void receiveRaycastEntityResultPacket(ServerboundRaycastEntityResultPacket packet, ServerPlayer player, PacketSender responseSender) {
        final var entity = player.level.getEntity(packet.entityIdentifier());

        if (entity == null) {
            return;
        }

        if (!GeomUtil.isEntityTargetValid(player, entity)) {
            return;
        }

        final var targets = Targets.of(Target.ofEntity(entity));

        notifyRaycastResultListeners(player, targets);
    }

    private static void notifyRaycastResultListeners(ServerPlayer player, Targets targets) {
        for (final var listener : RAYCAST_CONSUMERS.computeIfAbsent(player.getStringUUID(), uuid -> Collections.emptyList())) {
            listener.accept(targets);
        }

        RAYCAST_CONSUMERS.remove(player.getStringUUID());
    }

    @Override
    public void onInitialize() {
        LOGGER.info(String.format("Initialising %s version %s!", MOD_ID, VERSION));

        SpellcraftItems.initialise();
        SpellcraftBlocks.initialise();
        SpellcraftBlockEntityTypes.initialise();
        SpellcraftMobEffects.initialise();
        SpellcraftMenuTypes.initialise();

        SpellcraftShapes.initialise();
        SpellcraftEffects.initialise();
        SpellcraftModifiers.initialise();
        SpellcraftAttributeTypes.initialise();
        SpellcraftModifierHandlers.initialise();

        registerPacketReceivers();
    }
}