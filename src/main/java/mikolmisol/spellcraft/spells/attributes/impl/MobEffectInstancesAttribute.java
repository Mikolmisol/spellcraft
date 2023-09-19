package mikolmisol.spellcraft.spells.attributes.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AllArgsConstructor
public final class MobEffectInstancesAttribute implements Attribute {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "mob_effect_instances");
    @Getter
    @Setter
    private List<MobEffectInstance> mobEffectInstances;

    @Override
    public @NotNull AttributeType<?> getType() {
        return SpellcraftAttributeTypes.MOB_EFFECT_INSTANCES;
    }
}
