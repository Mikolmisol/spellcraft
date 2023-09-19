package mikolmisol.spellcraft;

import mikolmisol.spellcraft.accessors.PlayerManaAccessor;
import mikolmisol.spellcraft.block_entity_renderers.SpellcraftBlockEntityRenderers;
import mikolmisol.spellcraft.block_render_types.SpellcraftBlockRenderTypes;
import mikolmisol.spellcraft.entity_renderers.SpellcraftEntityRenderers;
import mikolmisol.spellcraft.item_renderers.SpellcraftItemRenderers;
import mikolmisol.spellcraft.networking.SpellcraftClientNetworking;
import mikolmisol.spellcraft.packets.clientbound.ClientboundRequestRaycastPacket;
import mikolmisol.spellcraft.packets.clientbound.ClientboundUpdateManaStoragePacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundRaycastBlockResultPacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundRaycastEntityResultPacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundShiftScrollPacket;
import mikolmisol.spellcraft.render_layers.SpellcraftRenderLayers;
import mikolmisol.spellcraft.screens.SpellcraftScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class SpellcraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SpellcraftRenderLayers.initialise();
        SpellcraftItemRenderers.initialise();
        SpellcraftBlockRenderTypes.initialise();
        SpellcraftBlockEntityRenderers.initialise();
        SpellcraftEntityRenderers.initialise();
        SpellcraftScreens.initialise();
        SpellcraftClientNetworking.initialise();


    }
}

