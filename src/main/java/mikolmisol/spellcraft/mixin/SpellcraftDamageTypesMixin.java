package mikolmisol.spellcraft.mixin;

import mikolmisol.spellcraft.damage.SpellcraftDamageTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTypes.class)
public interface SpellcraftDamageTypesMixin {

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "bootstrap",
                    args = net.minecraft.data.worldgen.BootstapContext.class
            )
    )
    private static void onBootstrap(BootstapContext<DamageType> context, CallbackInfo callback) {
        context.register(
                SpellcraftDamageTypes.ARCANE,
                new DamageType(
                        "arcane", //msgId
                        0.1f
                )
        );

        context.register(
                SpellcraftDamageTypes.FROST,
                new DamageType(
                        "frost",
                        0.1f,
                        DamageEffects.FREEZING
                )
        );

        context.register(
                SpellcraftDamageTypes.FIRE,
                new DamageType(
                        "fire",
                        0.1f,
                        DamageEffects.BURNING
                )
        );

        context.register(
                SpellcraftDamageTypes.LIGHTNING,
                new DamageType(
                        "lightning",
                        0.1f
                )
        );
    }
}
