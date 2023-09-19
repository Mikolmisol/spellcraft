package mikolmisol.spellcraft.data.tags;

import mikolmisol.spellcraft.damage.SpellcraftDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

final class SpellcraftDamageTypeTagProvider extends FabricTagProvider<DamageType> {

    public SpellcraftDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(DamageTypeTags.IS_FIRE)
                .add(SpellcraftDamageTypes.FIRE);

        getOrCreateTagBuilder(DamageTypeTags.IS_FREEZING)
                .add(SpellcraftDamageTypes.FROST);

        getOrCreateTagBuilder(DamageTypeTags.IS_LIGHTNING)
                .add(SpellcraftDamageTypes.LIGHTNING);

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(SpellcraftDamageTypes.ARCANE);

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .add(SpellcraftDamageTypes.ARCANE);
    }
}
