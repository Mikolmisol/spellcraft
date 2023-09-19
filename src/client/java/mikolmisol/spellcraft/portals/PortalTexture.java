package mikolmisol.spellcraft.portals;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import lombok.Getter;

import static com.mojang.blaze3d.platform.GlConst.*;

public final class PortalTexture implements AutoCloseable {

    private static final int WIDTH = 48;

    private static final int HEIGHT = 48;

    @Getter
    private final int colorIdentifier;

    @Getter
    private final int depthIdentifier;

    public PortalTexture() {
        this.depthIdentifier = GlStateManager._genTexture();

        GlStateManager._bindTexture(depthIdentifier);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_NONE);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        GlStateManager._texImage2D(GL_TEXTURE_2D, GL_NONE, GL_DEPTH_COMPONENT, WIDTH, HEIGHT, GL_NONE, GL_DEPTH_COMPONENT, GL_FLOAT, null);

        this.colorIdentifier = GlStateManager._genTexture();

        GlStateManager._bindTexture(colorIdentifier);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        GlStateManager._texImage2D(GL_TEXTURE_2D, GL_NONE, GL_RGBA8, WIDTH, HEIGHT, GL_NONE, GL_RGBA, GL_UNSIGNED_BYTE, null);
    }

    @Override
    public void close() {
        TextureUtil.releaseTextureId(colorIdentifier);
        TextureUtil.releaseTextureId(depthIdentifier);
    }
}
