package mikolmisol.spellcraft.models;
// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class VellumModel extends Model {

	private final ModelPart scroll3x3;

	private final ModelPart scroll2x2;

	private final ModelPart scroll1x1;

	private final ModelPart vellum;

	public VellumModel(ModelPart root) {
		super(RenderType::entityCutout);
		this.scroll3x3 = root.getChild("scroll3x3");
		this.scroll2x2 = root.getChild("scroll2x2");
		this.scroll1x1 = root.getChild("scroll1x1");
		this.vellum = root.getChild("vellum");
	}

	public static LayerDefinition createBodyLayer() {
		final var mesh = new MeshDefinition();
		final var root = mesh.getRoot();

		root.addOrReplaceChild(
				"scroll3x3",
				CubeListBuilder.create()
						.texOffs(0, 19)
						.addBox(-4.0F, -18.0F, -1.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(0, 0)
						.addBox(-5.0F, -18.0F, -1.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F)
		);

		root.addOrReplaceChild(
				"scroll2x2",
				CubeListBuilder.create()
						.texOffs(20, 6)
						.addBox(-4.0F, -22.0F, 0.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
						.texOffs(0, 15)
						.addBox(-5.0F, -22.0F, 0.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 29.0F, 0.0F)
		);

		root.addOrReplaceChild(
				"scroll1x1",
				CubeListBuilder.create()
						.texOffs(20, 10)
						.addBox(-4.0F, -25.0F, 1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(19, 19)
						.addBox(-5.0F, -25.0F, 1.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 33.0F, 0.0F)
		);

		root.addOrReplaceChild(
				"vellum",
				CubeListBuilder.create()
						.texOffs(0, 6)
						.addBox(-5.0F, -15.0F, 2.0F, 10.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F)
		);

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(Util.getMillis() / 5f));
		scroll3x3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();

		scroll2x2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		scroll1x1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

		vellum.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}