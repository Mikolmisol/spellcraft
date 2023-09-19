package mikolmisol.spellcraft.models;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.entities.impl.SkeletonMage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

@Environment(EnvType.CLIENT)
public class SkeletonMageModel extends HumanoidModel<SkeletonMage> {
    public SkeletonMageModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        final var mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        final var part = mesh.getRoot();
        part.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        part.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        part.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        part.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void prepareMobModel(SkeletonMage mob, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;

        if (mob.getMainArm() == HumanoidArm.RIGHT) {
            this.rightArmPose = ArmPose.TOOT_HORN;
        } else {
            this.leftArmPose = ArmPose.TOOT_HORN;
        }

        super.prepareMobModel(mob, f, g, h);
    }

    @Override
    public void setupAnim(SkeletonMage mage, float f, float g, float h, float i, float j) {
        super.setupAnim(mage, f, g, h, i, j);

        float k = Mth.sin(this.attackTime * 3.1415927F);
        float l = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * 3.1415927F);
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.yRot = -(0.1F - k * 0.6F);
        this.leftArm.yRot = 0.1F - k * 0.6F;
        this.rightArm.xRot = -1.5707964F;
        this.leftArm.xRot = -1.5707964F;
        ModelPart var10000 = this.rightArm;
        var10000.xRot -= k * 1.2F - l * 0.4F;
        var10000 = this.leftArm;
        var10000.xRot -= k * 1.2F - l * 0.4F;
        AnimationUtils.bobArms(this.rightArm, this.leftArm, h);
    }

    @Override
    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        float f = humanoidArm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        ModelPart modelPart = this.getArm(humanoidArm);
        modelPart.x += f;
        modelPart.translateAndRotate(poseStack);
        modelPart.x -= f;
    }
}
