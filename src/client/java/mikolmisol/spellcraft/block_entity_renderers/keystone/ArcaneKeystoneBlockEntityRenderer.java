package mikolmisol.spellcraft.block_entity_renderers.keystone;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.accessors.PortalsAccessor;
import mikolmisol.spellcraft.block_entities.portal.ArcaneKeystoneBlockEntity;
import mikolmisol.spellcraft.portals.Portal;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class ArcaneKeystoneBlockEntityRenderer implements BlockEntityRenderer<ArcaneKeystoneBlockEntity> {

    private final LevelRenderer levelRenderer;

    private final PortalsAccessor portalsAccessor;

    public ArcaneKeystoneBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.levelRenderer = Minecraft.getInstance().levelRenderer;
        this.portalsAccessor = JavaUtil.cast(levelRenderer);
    }

    @Override
    public void render(ArcaneKeystoneBlockEntity keystone, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {

        final var position = Vec3.atCenterOf(keystone.getBlockPos()).add(0, 2, 0);

        final var boundingBox = new AABB(
                position.subtract(1.5, 1.5, 0),
                position.add(1.5, 1.5, 0)
        );

        final var portals = portalsAccessor.spellcraft_getPortals();

        portals.computeIfAbsent(
                position,
                absentPosition -> new Portal( // To prevent the unnecessary construction of new Portals
                        absentPosition,
                        boundingBox,
                        absentPosition.vectorTo(new Vec3(absentPosition.x, absentPosition.y, absentPosition.z + 1)).toVector3f(),
                        absentPosition.vectorTo(new Vec3(absentPosition.x + 1, absentPosition.y, absentPosition.z)).toVector3f()
                )
        );
    }
}
