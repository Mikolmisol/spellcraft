package mikolmisol.spellcraft.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mikolmisol.spellcraft.entities.impl.SpellProjectile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class SpellProjectileModel extends EntityModel<SpellProjectile> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/shulker/spark.png");

    private final ModelPart root;
    private final ModelPart main;

    public SpellProjectileModel(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        final var mesh = new MeshDefinition();
        final var part = mesh.getRoot();

        part.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F).texOffs(0, 10).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F).texOffs(20, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(SpellProjectile entity, float f, float g, float h, float i, float j) {
        this.main.yRot = i * 0.017453292F;
        this.main.xRot = j * 0.017453292F;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float r, float g, float b, float alpha) {
        root.render(matrices, vertices, light, overlay, r, g, b, alpha);
    }
}
