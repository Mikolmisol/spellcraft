package mikolmisol.spellcraft.entity_renderers;

import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class SpellcraftEntityRenderers {

    private SpellcraftEntityRenderers() {
    }

    public static void initialise() {
        EntityRendererRegistry.register(SpellcraftEntityTypes.SPELL_BOLT, SpellBoltRenderer::new);
        EntityRendererRegistry.register(SpellcraftEntityTypes.SPELL_PROJECTILE, SpellProjectileRenderer::new);
        EntityRendererRegistry.register(SpellcraftEntityTypes.MANA_SPARK, ManaSparkRenderer::new);
        EntityRendererRegistry.register(SpellcraftEntityTypes.MANA_SLIME, ManaSlimeRenderer::new);
        EntityRendererRegistry.register(SpellcraftEntityTypes.SPELL_PROJECTILE, SpellProjectileRenderer::new);
        EntityRendererRegistry.register(SpellcraftEntityTypes.SKELETON_MAGE, SkeletonMageRenderer::new);
        EntityRendererRegistry.register(SpellcraftEntityTypes.ARCANE_BROOM, ArcaneBroomRenderer::new);
    }
}
