package mikolmisol.spellcraft.mixin.client;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.util.JavaUtil;
import mikolmisol.spellcraft.util.SpellCastingAnimationHelper;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WitchModel.class)
public abstract class SpellcraftWitchModelMixin<T extends Entity> extends VillagerModel<T> {

    @Unique
    private ModelPart spellcraft_rightArm;

    @Unique
    private ModelPart spellcraft_leftArm;

    @Unique
    private ModelPart spellcraft_arms;

    public SpellcraftWitchModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "createBodyLayer",
                    ret = LayerDefinition.class
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private static LayerDefinition spellcraft_onCreateBodyLayer(CallbackInfoReturnable<LayerDefinition> callback, MeshDefinition meshDefinition, PartDefinition partDefinition) {
        // add mobile illager arms to enable casting animation use
        partDefinition.addOrReplaceChild(Spellcraft.MOD_ID + "_right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild(Spellcraft.MOD_ID + "_left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));

        callback.setReturnValue(callback.getReturnValue());
        return null;
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "<init>",
                    args = ModelPart.class
            )
    )
    public void spellcraft_onInit(ModelPart root, CallbackInfo callback) {
        this.spellcraft_leftArm = root.getChild(Spellcraft.MOD_ID + "_left_arm");
        this.spellcraft_rightArm = root.getChild(Spellcraft.MOD_ID + "_right_arm");
        this.spellcraft_arms = root.getChild("arms");
    }

    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "setupAnim",
                    args = {Entity.class, float.class, float.class, float.class, float.class, float.class}
            )
    )
    public void spellcraft_onSetupAnim(T witch, float f, float g, float h, float i, float j, CallbackInfo callback) {
        if (riding) {
            rightLeg.xRot = -1.4137167f;
            rightLeg.yRot = 0.31415927f;
            rightLeg.zRot = 0.07853982f;
            leftLeg.xRot = -1.4137167f;
            leftLeg.yRot = -0.31415927f;
            leftLeg.zRot = -0.07853982f;
            spellcraft_rightArm.xRot = -0.62831855F;
            spellcraft_rightArm.yRot = 0.0F;
            spellcraft_rightArm.zRot = 0.0F;
            spellcraft_leftArm.xRot = -0.62831855F;
            spellcraft_leftArm.yRot = 0.0F;
            spellcraft_leftArm.zRot = 0.0F;
        } else {
            spellcraft_rightArm.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F;
            spellcraft_rightArm.yRot = 0.0F;
            spellcraft_rightArm.zRot = 0.0F;
            spellcraft_leftArm.xRot = Mth.cos(f * 0.6662F) * 2.0F * g * 0.5F;
            spellcraft_leftArm.yRot = 0.0F;
            spellcraft_leftArm.zRot = 0.0F;
            rightLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g * 0.5F;
            rightLeg.yRot = 0.0F;
            rightLeg.zRot = 0.0F;
            leftLeg.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
            leftLeg.yRot = 0.0F;
            leftLeg.zRot = 0.0F;
        }

        if (witch instanceof Caster caster && caster.spellcraft_isCasting()) {
            spellcraft_arms.visible = false;
            spellcraft_rightArm.visible = true;
            spellcraft_leftArm.visible = true;

            SpellCastingAnimationHelper.animateHands(JavaUtil.cast(this), h);

        } else {

            spellcraft_arms.visible = true;
            spellcraft_rightArm.visible = false;
            spellcraft_leftArm.visible = false;
        }
    }
}
