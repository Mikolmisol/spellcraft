package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class SpellCastingAnimationHelper {

    public void animateHands(@NotNull HumanoidModel<?> model, float animationProgress) {
        model.rightArm.z = 0.0f;
        model.rightArm.x = -5.0f;
        model.leftArm.z = 0.0f;
        model.leftArm.x = 5.0f;
        model.rightArm.xRot = Mth.cos(animationProgress * 0.6662f) * 0.25f;
        model.leftArm.xRot = Mth.cos(animationProgress * 0.6662f) * 0.25f;
        model.rightArm.zRot = 2.3561945f;
        model.leftArm.zRot = -2.3561945f;
        model.rightArm.yRot = 0.0f;
        model.leftArm.yRot = 0.0f;
    }
}
