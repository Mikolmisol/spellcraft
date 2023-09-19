package mikolmisol.spellcraft.mob_effects;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.mob_effects.impl.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
public class SpellcraftMobEffects {
    public final MobEffect MANA_REGEN;

    public final MobEffect SILENCE;

    public final MobEffect BLOOD_CONTROL;

    public final MobEffect ARCANE_BARRIER;

    public final MobEffect POLYMORPH;

    static {
        MANA_REGEN = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MOD_ID, "mana_regen"), new ManaRegen());

        SILENCE = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MOD_ID, "silence"), new Silence());

        BLOOD_CONTROL = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MOD_ID, "blood_control"), new BloodControl());

        ARCANE_BARRIER = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MOD_ID, "arcane_barrier"), new ArcaneShield());

        POLYMORPH = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MOD_ID, "polymorph"), new Polymorph());
    }

    public static @NotNull MobEffectInstance createInstance(@NotNull MobEffect effect, int durationInTicks, int amplifier) {
        if (effect == ARCANE_BARRIER) {
            return new MobEffectInstance(
                    effect,
                    durationInTicks,
                    amplifier,
                    false,
                    false
            );
        }
        else if (effect == BLOOD_CONTROL) {
            return new BloodControl.BloodControlInstance(
                    effect,
                    durationInTicks
            );
        }

        return new MobEffectInstance(
                effect,
                durationInTicks,
                amplifier
        );
    }

    public void initialise() {
    }
}
