package mikolmisol.spellcraft.entities;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.entities.impl.*;
import mikolmisol.spellcraft.spells.casting.impl.EntityBackedSpellCastEvent;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
public class SpellcraftEntityTypes {
    public final EntityType<SpellBolt> SPELL_BOLT;

    public final EntityType<SpellProjectile> SPELL_PROJECTILE;

    public final EntityType<ManaSpark> MANA_SPARK;

    public final EntityType<ManaSlime> MANA_SLIME;

    public final EntityType<SkeletonMage> SKELETON_MAGE;

    public final EntityType<ArcaneBroom> ARCANE_BROOM;

    public final EntityType<EntityBackedSpellCastEvent> SPELL_CAST_EVENT;

    static {
        SPELL_BOLT = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "spell_bolt"), FabricEntityTypeBuilder.create(MobCategory.MISC, SpellBolt::new).disableSummon().disableSaving().fireImmune().build());

        SPELL_PROJECTILE = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "spell_projectile"), FabricEntityTypeBuilder.create(MobCategory.MISC, SpellProjectile::new).disableSummon().disableSaving().fireImmune().build());

        MANA_SPARK = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "mana_spark"), FabricEntityTypeBuilder.create(MobCategory.MISC, ManaSpark::new).disableSummon().disableSaving().fireImmune().build());

        SPELL_CAST_EVENT = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "spell_cast_event"), FabricEntityTypeBuilder.create(MobCategory.MISC, EntityBackedSpellCastEvent::new).dimensions(EntityDimensions.fixed(0.1f, 0.1f)).build());

        MANA_SLIME = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "mana_slime"), FabricEntityTypeBuilder.create(MobCategory.CREATURE, ManaSlime::new).dimensions(EntityDimensions.scalable(0.2f, 0.2f)).trackRangeBlocks(10).build());
        FabricDefaultAttributeRegistry.register(MANA_SLIME, ManaSlime.createMobAttributes());

        SKELETON_MAGE = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "skeleton_mage"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, SkeletonMage::new).dimensions(EntityDimensions.fixed(1, 2)).build());
        FabricDefaultAttributeRegistry.register(SKELETON_MAGE, SkeletonMage.createAttributes());

        ARCANE_BROOM = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "arcane_broom"), FabricEntityTypeBuilder.create(MobCategory.MISC, ArcaneBroom::new).dimensions(EntityDimensions.fixed(2, 1)).build());
        FabricDefaultAttributeRegistry.register(ARCANE_BROOM, ArcaneBroom.createAttributes());
    }

    public void initialise() {
    }
}
