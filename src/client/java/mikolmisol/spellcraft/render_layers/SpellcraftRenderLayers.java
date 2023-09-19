package mikolmisol.spellcraft.render_layers;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.models.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

@UtilityClass
public class SpellcraftRenderLayers {
    public final ModelLayerLocation SPELL_BOOK = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "spell_book"), "main");

    public final ModelLayerLocation VELLUM = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "vellum"), "vellum");

    public final ModelLayerLocation RUNE = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "rune"), "main");

    public final ModelLayerLocation MANA_SLIME_BODY = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "mana_slime"), "main");

    public final ModelLayerLocation MANA_SLIME_OUTER = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "mana_slime"), "outer");

    public final ModelLayerLocation SKELETON_MAGE_BODY = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "skeleton_mage"), "main");

    public final ModelLayerLocation WITCH_HAT = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "witch_hat"), "main");

    public final ModelLayerLocation ARCANE_CORE_PYLON = new ModelLayerLocation(new ResourceLocation(Spellcraft.MOD_ID, "arcane_core_pylon"), "main");
    
    public void initialise() {
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.SPELL_BOOK, SpellBookModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.VELLUM, VellumModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.RUNE, RuneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.MANA_SLIME_BODY, ManaSlimeModel::createInnerBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.MANA_SLIME_OUTER, ManaSlimeModel::createOuterBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.SKELETON_MAGE_BODY, SkeletonMageModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.WITCH_HAT, WitchHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpellcraftRenderLayers.ARCANE_CORE_PYLON, ArcaneCorePylonModel::createBodyLayer);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(ArcaneShieldLayer::register);
    }
}
