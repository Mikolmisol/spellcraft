package mikolmisol.spellcraft.render_types;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import lombok.experimental.UtilityClass;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Unmodifiable;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
public class SpellcraftRenderTypes {

    private final ResourceLocation RAINBOW_HUE_TEXTURE = new ResourceLocation("spellcraft", "textures/misc/hue.png");
    private final RenderStateShard.TextureStateShard RAINBOW_TEXTURE_STATE_SHARD = new RenderStateShard.TextureStateShard(RAINBOW_HUE_TEXTURE, true, true);
    private final String RAINBOW_TRIANGLES_IDENTIFIER = new ResourceLocation(MOD_ID, "rainbow_triangles").toString();
    private final String RAINBOW_SQUARES_IDENTIFIER = new ResourceLocation(MOD_ID, "rainbow_squares").toString();
    private final ResourceLocation ARCANE_BARRIER_TEXTURE = new ResourceLocation("spellcraft:textures/misc/arcane_barrier.png");
    private final String SPELL_AURA_IDENTIFIER = new ResourceLocation(MOD_ID, "spell_aura").toString();
    private final String SPELL_BODY_IDENTIFIER = new ResourceLocation(MOD_ID, "spell_body").toString();
    private final String MANA_CRYSTAL_TRIANGLES_IDENTIFIER = new ResourceLocation(MOD_ID, "mana_crystal_triangles").toString();
    private final String MANA_CRYSTAL_SQUARES_IDENTIFIER = new ResourceLocation(MOD_ID, "mana_crystal_squares").toString();
    private final RenderType SPELL_PROJECTILES = RenderType.create(SPELL_BODY_IDENTIFIER, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 128, false, false, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_EYES_SHADER).setTransparencyState(RenderType.LIGHTNING_TRANSPARENCY).setLightmapState(RenderType.NO_LIGHTMAP).setCullState(RenderStateShard.NO_CULL).setOverlayState(RenderType.NO_OVERLAY).createCompositeState(true));
    private final RenderType SPELL_BOLTS = RenderType.create("spell_bolt", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_LIGHTNING_SHADER).setTexturingState(RenderStateShard.GLINT_TEXTURING).setTextureState(new RenderStateShard.TextureStateShard(new ResourceLocation("textures/misc/enchanted_item_glint.png"), false, false)).setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).setCullState(RenderStateShard.NO_CULL).setLightmapState(RenderStateShard.NO_LIGHTMAP).setOverlayState(RenderStateShard.NO_OVERLAY).createCompositeState(RenderType.OutlineProperty.IS_OUTLINE));
    private final RenderType MANA_CRYSTAL_TRIANGLES = RenderType.create(MANA_CRYSTAL_TRIANGLES_IDENTIFIER, DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, 256, false, true, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(new ResourceLocation("minecraft:textures/misc/enchanted_item_glint.png"), true, true)).setTexturingState(RenderStateShard.GLINT_TEXTURING).setShaderState(RenderType.RENDERTYPE_LIGHTNING_SHADER).setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).createCompositeState(RenderType.OutlineProperty.IS_OUTLINE));
    private final RenderType MANA_CRYSTAL_SQUARES = RenderType.create(MANA_CRYSTAL_SQUARES_IDENTIFIER, DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(new ResourceLocation("minecraft:textures/misc/enchanted_item_glint.png"), true, true)).setTexturingState(RenderStateShard.GLINT_TEXTURING).setShaderState(RenderType.RENDERTYPE_LIGHTNING_SHADER).setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).createCompositeState(RenderType.OutlineProperty.IS_OUTLINE));

    public static RenderType forSpellBolts() {
        return SPELL_BOLTS;
    }

    public static RenderType forSpellProjectiles() {
        return SPELL_PROJECTILES;
    }

    public static RenderType forSpellAura(ResourceLocation texture, float textureOffsetX, float textureOffsetY) {
        return RenderType.create(
                SPELL_AURA_IDENTIFIER,
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_ENERGY_SWIRL_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setTexturingState(new RenderStateShard.OffsetTexturingStateShard(textureOffsetX, textureOffsetY)).setTransparencyState(RenderType.LIGHTNING_TRANSPARENCY).setCullState(RenderStateShard.NO_CULL).createCompositeState(false)
        );
    }

    public static RenderType forArcaneBarrierEntity(float textureOffsetX, float textureOffsetY) {
        return RenderType.create(
                "arcane_barrier_entity",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderType.RENDERTYPE_ENERGY_SWIRL_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(ARCANE_BARRIER_TEXTURE, false, false))
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(textureOffsetX, textureOffsetY))
                        .setTransparencyState(RenderType.LIGHTNING_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false));
    }

    public static RenderType forArcaneBarrier(float textureOffsetX, float textureOffsetY) {
        return RenderType.create(
                "arcane_barrier",
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.TRIANGLE_STRIP,
                256,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderType.RENDERTYPE_ENERGY_SWIRL_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(ARCANE_BARRIER_TEXTURE, false, false))
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(textureOffsetX, textureOffsetY))
                        .setTransparencyState(RenderType.LIGHTNING_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false));
    }

    public static RenderType forManaCrystalTriangles() {
        return MANA_CRYSTAL_TRIANGLES;
    }

    public static RenderType forManaCrystalSquares() {
        return MANA_CRYSTAL_SQUARES;
    }

    public static RenderType forRainbowTriangles() {
        return RenderType.create(
                RAINBOW_TRIANGLES_IDENTIFIER,
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.TRIANGLES,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setTextureState(RAINBOW_TEXTURE_STATE_SHARD)
                        .setShaderState(RenderType.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(0, Util.getMillis() / 25000f))
                        .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
                        .createCompositeState(RenderType.OutlineProperty.IS_OUTLINE));
    }

    public static RenderType forRainbowSquares() {
        return RenderType.create(
                RAINBOW_SQUARES_IDENTIFIER,
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setTextureState(RAINBOW_TEXTURE_STATE_SHARD)
                        .setShaderState(RenderType.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(0, Util.getMillis() / 25000f))
                        .setTransparencyState(RenderType.GLINT_TRANSPARENCY)
                        .createCompositeState(RenderType.OutlineProperty.IS_OUTLINE));
    }
}
