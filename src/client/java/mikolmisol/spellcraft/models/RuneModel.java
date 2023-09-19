package mikolmisol.spellcraft.models;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;
import java.util.function.Function;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;
import static net.minecraft.client.renderer.RenderStateShard.*;

@Environment(EnvType.CLIENT)
public class RuneModel extends Model {
    public static final Function<ResourceLocation, RenderType> RENDER_TYPE = Util.memoize((resourceLocation) -> {
        RenderType.CompositeState compositeState = RenderType.CompositeState
                .builder()
                .setShaderState(RENDERTYPE_BEACON_BEAM_SHADER)
                .setTextureState(new TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setLightmapState(NO_LIGHTMAP)
                .setOverlayState(NO_OVERLAY)
                .setCullState(NO_CULL)
                .createCompositeState(false);

        return RenderType.create(MOD_ID + ":rune", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
    });
    private static final ResourceLocation[] RUNES = new ResourceLocation[]{
            new ResourceLocation("textures/particle/sga_a.png"),
            new ResourceLocation("textures/particle/sga_b.png"),
            new ResourceLocation("textures/particle/sga_c.png"),
            new ResourceLocation("textures/particle/sga_d.png"),
            new ResourceLocation("textures/particle/sga_e.png"),
            new ResourceLocation("textures/particle/sga_f.png"),
            new ResourceLocation("textures/particle/sga_g.png"),
            new ResourceLocation("textures/particle/sga_h.png"),
            new ResourceLocation("textures/particle/sga_i.png"),
            new ResourceLocation("textures/particle/sga_j.png"),
            new ResourceLocation("textures/particle/sga_k.png"),
            new ResourceLocation("textures/particle/sga_l.png"),
            new ResourceLocation("textures/particle/sga_m.png"),
            new ResourceLocation("textures/particle/sga_n.png"),
            new ResourceLocation("textures/particle/sga_o.png"),
            new ResourceLocation("textures/particle/sga_p.png"),
            new ResourceLocation("textures/particle/sga_q.png"),
            new ResourceLocation("textures/particle/sga_r.png"),
            new ResourceLocation("textures/particle/sga_s.png"),
            new ResourceLocation("textures/particle/sga_t.png"),
            new ResourceLocation("textures/particle/sga_u.png"),
            new ResourceLocation("textures/particle/sga_v.png"),
            new ResourceLocation("textures/particle/sga_w.png"),
            new ResourceLocation("textures/particle/sga_x.png"),
            new ResourceLocation("textures/particle/sga_y.png"),
            new ResourceLocation("textures/particle/sga_z.png"),
    };
    private final ModelPart root;
    private final ModelPart rune;
    private final Random random;
    public ResourceLocation texture;

    public RuneModel(ModelPart root) {
        super(RENDER_TYPE);
        this.root = root;
        this.rune = root.getChild("rune");
        this.random = new Random();
        this.texture = RUNES[random.nextInt(0, 26)];
    }

    public static LayerDefinition createBodyLayer() {
        final var mesh = new MeshDefinition();
        final var root = mesh.getRoot();
        root.addOrReplaceChild("rune", CubeListBuilder.create().texOffs(0, 0).addBox(-8, -8, 0, 8, 8, 0), PartPose.ZERO);

        return LayerDefinition.create(mesh, 16, 16);
    }

    public void randomiseTexture() {
        texture = RUNES[random.nextInt(0, 26)];
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int i, int j, float f, float g, float h, float k) {
        root.render(matrices, vertices, i, j, f, g, h, k);
    }
}
