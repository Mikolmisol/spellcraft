package mikolmisol.spellcraft.damage;

import lombok.experimental.UtilityClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
public class SpellcraftDamageTypes {
    public final ResourceKey<DamageType> ARCANE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "arcane"));

    public final ResourceKey<DamageType> FROST = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "frost"));

    public final ResourceKey<DamageType> FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "fire"));

    public final ResourceKey<DamageType> LIGHTNING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "lightning"));

    public void initialise() {
    }
}
