package mikolmisol.spellcraft.mixin.client;

import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.util.JavaUtil;
import mikolmisol.spellcraft.util.SpellCastingAnimationHelper;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class SpellcraftHumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
    @Inject(
            at = @At("RETURN"),
            target = @Desc(
                    value = "setupAnim",
                    args = {LivingEntity.class, float.class, float.class, float.class, float.class, float.class}
            )
    )
    public void onSetupAnim(T livingEntity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo callback) {
        if (livingEntity instanceof Caster caster && caster.spellcraft_isCasting()) {
            SpellCastingAnimationHelper.animateHands(JavaUtil.cast(this), animationProgress);
        }
    }
}
