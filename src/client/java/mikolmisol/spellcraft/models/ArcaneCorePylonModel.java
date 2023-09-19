package mikolmisol.spellcraft.models;// Made with Blockbench 4.7.2

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mikolmisol.spellcraft.Spellcraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ArcaneCorePylonModel extends Model {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Spellcraft.MOD_ID, "textures/block/arcane_core_pylon.png");
    public final RenderType type;
    private final ModelPart pillar;
    private final ModelPart ring;

    public ArcaneCorePylonModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.pillar = root.getChild("pillar");
        this.ring = root.getChild("ring");
        this.type = renderType(TEXTURE);
    }

    public static LayerDefinition createBodyLayer() {
        final var mesh = new MeshDefinition();
        final var root = mesh.getRoot();

        final var pillar = root.addOrReplaceChild("pillar", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        pillar.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(0, 41).addBox(-3.0F, -27.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, -0.1745F));

        pillar.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(40, 33).addBox(-4.0F, -20.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, -0.1309F));

        pillar.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(0, 21).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, -0.0873F));

        pillar.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -5.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        final var ring = root.addOrReplaceChild("ring", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        ring.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(0, 4).addBox(1.0F, -28.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 5).addBox(-2.0F, -28.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -28.0F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-2.0F, -28.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, -0.1745F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        pillar.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        ring.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}