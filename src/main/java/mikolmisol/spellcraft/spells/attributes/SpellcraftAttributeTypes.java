package mikolmisol.spellcraft.spells.attributes;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.attributes.impl.*;
import net.minecraft.core.Registry;

@UtilityClass
public final class SpellcraftAttributeTypes {
    public final AttributeType<VeinmineAttribute> VEINMINE;

    public final AttributeType<DurationAttribute> DURATION;

    public final AttributeType<FireDamageAttribute> FIRE_DAMAGE;

    public final AttributeType<FrostDamageAttribute> FROST_DAMAGE;

    public final AttributeType<LightningDamageAttribute> LIGHTNING_DAMAGE;

    public final AttributeType<ArcaneDamageAttribute> ARCANE_DAMAGE;

    public final AttributeType<HealingAttribute> HEALING;

    public final AttributeType<RangeAttribute> RANGE;

    public final AttributeType<BlockClipContextAttribute> BLOCK_CLIP_CONTEXT;

    public final AttributeType<FluidClipContextAttribute> FLUID_CLIP_CONTEXT;

    public final AttributeType<MobEffectInstancesAttribute> MOB_EFFECT_INSTANCES;

    public final AttributeType<MiningLevelAttribute> MINING_LEVEL;

    public static final AttributeType<GravityAttribute> GRAVITY;

    public static final AttributeType<SilkTouchAttribute> SILK_TOUCH;

    static {
        VEINMINE = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, VeinmineAttribute.IDENTIFIER, new AttributeType<>(VeinmineAttribute.IDENTIFIER));

        DURATION = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, DurationAttribute.IDENTIFIER, new AttributeType<>(DurationAttribute.IDENTIFIER));

        HEALING = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, HealingAttribute.IDENTIFIER, new AttributeType<>(HealingAttribute.IDENTIFIER));

        RANGE = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, RangeAttribute.IDENTIFIER, new AttributeType<>(RangeAttribute.IDENTIFIER));

        BLOCK_CLIP_CONTEXT = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, BlockClipContextAttribute.IDENTIFIER, new AttributeType<>(BlockClipContextAttribute.IDENTIFIER));

        FLUID_CLIP_CONTEXT = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, FluidClipContextAttribute.IDENTIFIER, new AttributeType<>(FluidClipContextAttribute.IDENTIFIER));

        MOB_EFFECT_INSTANCES = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, MobEffectInstancesAttribute.IDENTIFIER, new AttributeType<>(MobEffectInstancesAttribute.IDENTIFIER));

        MINING_LEVEL = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, MiningLevelAttribute.IDENTIFIER, new AttributeType<>(MiningLevelAttribute.IDENTIFIER));

        FIRE_DAMAGE = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, FireDamageAttribute.IDENTIFIER, new AttributeType<>(FireDamageAttribute.IDENTIFIER));

        FROST_DAMAGE = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, FrostDamageAttribute.IDENTIFIER, new AttributeType<>(FrostDamageAttribute.IDENTIFIER));

        LIGHTNING_DAMAGE = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, LightningDamageAttribute.IDENTIFIER, new AttributeType<>(LightningDamageAttribute.IDENTIFIER));

        ARCANE_DAMAGE = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, ArcaneDamageAttribute.IDENTIFIER, new AttributeType<>(ArcaneDamageAttribute.IDENTIFIER));

        GRAVITY = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, GravityAttribute.IDENTIFIER, new AttributeType<>(GravityAttribute.IDENTIFIER));

        SILK_TOUCH = Registry.register(SpellcraftRegistries.ATTRIBUTE_TYPE, SilkTouchAttribute.IDENTIFIER, new AttributeType<>(SilkTouchAttribute.IDENTIFIER));
    }

    public void initialise() {
    }
}
