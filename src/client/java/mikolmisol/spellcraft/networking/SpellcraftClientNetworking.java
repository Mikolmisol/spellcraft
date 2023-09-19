package mikolmisol.spellcraft.networking;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.accessors.PlayerManaAccessor;
import mikolmisol.spellcraft.packets.clientbound.ClientboundRequestRaycastPacket;
import mikolmisol.spellcraft.packets.clientbound.ClientboundUpdateManaStoragePacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundRaycastBlockResultPacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundRaycastEntityResultPacket;
import mikolmisol.spellcraft.packets.serverbound.ServerboundShiftScrollPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;

@UtilityClass
public class SpellcraftClientNetworking {

    private void receiveUpdateManaStoragePacket(ClientboundUpdateManaStoragePacket packet, LocalPlayer player, PacketSender responseSender) {
        if (player instanceof PlayerManaAccessor accessor) {
            accessor.spellcraft_setManaAmount(packet.amount());
            accessor.spellcraft_setManaCapacity(packet.capacity());
        }
    }

    public void sendShiftScrollPacket(int integralAccumulatedScroll) {
        ClientPlayNetworking.send(new ServerboundShiftScrollPacket(integralAccumulatedScroll));
    }

    private void receiveRequestRaycastPacket(ClientboundRequestRaycastPacket packet, LocalPlayer player, PacketSender responseSender) {
        final var reach = packet.reach();

        final var client = Minecraft.getInstance();
        final var camera = client.getCameraEntity();

        if (camera == null) {
            return;
        }

        if (client.level == null) {
            return;
        }

        final var tickDelta = client.getDeltaFrameTime();

        final var eyePosition = camera.getEyePosition(tickDelta);
        final var viewVector = player.getViewVector(tickDelta);
        final var eyePositionPlusViewVector = eyePosition.add(viewVector.x * reach, viewVector.y * reach, viewVector.z * reach);

        final var blockHitResult = player.level.clip(
                new ClipContext(
                        eyePosition,
                        eyePositionPlusViewVector,
                        ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player
                )
        );

        var distanceSquared = reach * reach;

        if (blockHitResult != null) {
            distanceSquared = blockHitResult.getLocation().distanceToSqr(eyePosition);
        }

        final var viewVectorAt1 = camera.getViewVector(1.0F);
        final var expandedEyePosition = eyePosition.add(viewVectorAt1.x * reach, viewVectorAt1.y * reach, viewVectorAt1.z * reach);

        final var aabb = camera.getBoundingBox().expandTowards(viewVectorAt1.scale(reach)).inflate(1.0, 1.0, 1.0);

        final var entityHitResult = ProjectileUtil.getEntityHitResult(camera, eyePosition, expandedEyePosition, aabb, (entity) -> !entity.isSpectator() && entity.isPickable(), distanceSquared);

        if (entityHitResult == null && blockHitResult != null) {
            ClientPlayNetworking.send(
                    new ServerboundRaycastBlockResultPacket(
                            blockHitResult.getBlockPos(),
                            blockHitResult.getDirection()
                    )
            );

            return;
        }

        final var entityHitResultLocationVector = entityHitResult.getLocation();
        final var distanceFromEyePositionToEntity = eyePosition.distanceToSqr(entityHitResultLocationVector);

        if (distanceFromEyePositionToEntity < distanceSquared) {
            ClientPlayNetworking.send(
                    new ServerboundRaycastEntityResultPacket(
                            entityHitResult.getEntity().getId()
                    )
            );

            return;
        }

        if (blockHitResult != null) {
            ClientPlayNetworking.send(
                    new ServerboundRaycastBlockResultPacket(
                            blockHitResult.getBlockPos(),
                            blockHitResult.getDirection()
                    )
            );
        }
    }

    public void initialise() {
        ClientPlayNetworking.registerGlobalReceiver(ClientboundUpdateManaStoragePacket.TYPE, SpellcraftClientNetworking::receiveUpdateManaStoragePacket);
        ClientPlayNetworking.registerGlobalReceiver(ClientboundRequestRaycastPacket.TYPE, SpellcraftClientNetworking::receiveRequestRaycastPacket);
    }
}
