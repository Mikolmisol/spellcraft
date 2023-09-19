package mikolmisol.spellcraft.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mikolmisol.spellcraft.items.impl.SpellBookItem;
import mikolmisol.spellcraft.render_layers.SpellcraftRenderLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@Environment(EnvType.CLIENT)
public final class SpellBookModel extends Model {
    public static final ResourceLocation BOOK = new ResourceLocation(MOD_ID, "textures/item/spell_book.png");
    public final ModelPart leftPages;
    public final RuneModel leftRune = new RuneModel(Minecraft.getInstance().getEntityModels().bakeLayer(SpellcraftRenderLayers.RUNE));
    public final ModelPart rightPages;
    public final RuneModel rightRune = new RuneModel(Minecraft.getInstance().getEntityModels().bakeLayer(SpellcraftRenderLayers.RUNE));
    private final ModelPart root;
    private final ModelPart leftLid;
    private final ModelPart rightLid;
    private final ModelPart flipPage;
    private final Random random = new Random();
    private float glow;
    private float oldGlow;
    private float open;
    private float oldOpen;
    private boolean isOpening;
    private boolean isClosing;
    private float flip;
    private float oldFlip;
    private boolean flippingLeft;
    private boolean flippingRight;
    private ResourceLocation leftRuneTexture;

    private ResourceLocation rightRuneTexture;

    private SpellBookModel() {
        super(RenderType::entitySolid);
        this.root = Minecraft.getInstance().getEntityModels().bakeLayer(SpellcraftRenderLayers.SPELL_BOOK);
        this.leftLid = root.getChild("left_lid");
        this.rightLid = root.getChild("right_lid");
        this.leftPages = root.getChild("left_pages");
        this.rightPages = root.getChild("right_pages");
        this.flipPage = root.getChild("flip_page");
    }

    public static SpellBookModel initiallyClosed() {
        return new SpellBookModel();
    }

    public static SpellBookModel initiallyOpen() {
        final var model = initiallyClosed();
        model.open = 0.95f;
        return model;
    }

    public static LayerDefinition createBodyLayer() {
        final var mesh = new MeshDefinition();
        final var root = mesh.getRoot();
        root.addOrReplaceChild("left_lid", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), PartPose.offset(0.0F, 0.0F, -1.0F));
        root.addOrReplaceChild("right_lid", CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), PartPose.offset(0.0F, 0.0F, 1.0F));
        root.addOrReplaceChild("seam", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F), PartPose.rotation(0.0F, 1.5707964F, 0.0F));
        root.addOrReplaceChild("left_pages", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("right_pages", CubeListBuilder.create().texOffs(12, 10).addBox(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("flip_page", CubeListBuilder.create().texOffs(24, 10).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 32);
    }

    public float getGlow(float tick) {
        return Mth.lerp(tick, oldGlow, glow);
    }

    public boolean stepGlow() {
        if (glow < 0.5) {
            oldGlow = glow;
            glow += 0.01;
            return true;
        } else {
            glow = 0;
            oldGlow = glow;
            leftRune.randomiseTexture();
            rightRune.randomiseTexture();
            return false;
        }
    }

    public void open() {
        isOpening = true;
        isClosing = false;
    }

    public void openWithItem(SpellBookItem book, ItemStack item) {
        book.open(item);
    }

    public boolean isOpening() {
        return isOpening;
    }

    public float getOpen() {
        return open;
    }

    private void stepOpen() {
        oldOpen = open;

        if (isOpening) {
            if (open < 1.2f) {
                open += 0.05;
            } else {
                isOpening = false;
                open = 1.2f;
            }
        }
    }

    private void stepOpenWithItem(SpellBookItem book, ItemStack item) {
        if (book.isOpening(item)) {
            if (open < 1.2f) {
                isOpening = true;
            } else {
                isOpening = false;
                book.stopOpening(item);
            }
        }
    }

    public void close() {
        isClosing = true;
        isOpening = false;
    }

    public boolean isClosing() {
        return isClosing;
    }

    private void stepClose() {
        oldOpen = open;

        if (isClosing) {
            if (open > 0f) {
                open += 0.05;
            } else {
                isClosing = false;
                open = 0f;
            }
        }
    }

    public void instantlyClose() {
        open = 0f;
        oldOpen = open;
        isClosing = false;
        isOpening = false;
    }

    public void flipRight() {
        flip = 0.4f;
        flippingRight = true;
        flippingLeft = false;
    }

    public void flipRightWithItem(@NotNull SpellBookItem book, ItemStack item) {
        flipRight();
        book.stopFlippingLeft(item);
    }

    public void flipLeft() {
        flip = 1.1f;
        flippingLeft = true;
        flippingRight = false;
    }

    public void flipLeftWithItem(@NotNull SpellBookItem book, ItemStack item) {
        flipLeft();
        book.stopFlippingRight(item);
    }

    private void stepFlipLeft() {
        if (flippingLeft) {
            oldFlip = flip;

            flip -= 0.03;

            if (flip < 0.1) {
                flip = 1.1f;
                oldFlip = flip;
                flippingLeft = false;
            }
        }
    }

    private void stepFlipLeftWithItem(@NotNull SpellBookItem book, ItemStack item) {
        if (book.isFlippingLeft(item)) {
            if (flip < 0) {
                flippingLeft = false;
                book.stopFlippingLeft(item);
            }
        }
    }

    private void stepFlipRight() {
        if (flippingRight) {
            oldFlip = flip;

            flip += 0.03;

            if (flip > 1.4f) {
                flip = 0.4f;
                oldFlip = flip;
                flippingRight = false;
            }
        }
    }

    private void stepFlipRightWithItem(@NotNull SpellBookItem book, ItemStack item) {
        if (book.isFlippingRight(item)) {
            if (flip > 1.4f) {
                flippingRight = false;
                book.stopFlippingRight(item);
            }
        }
    }

    private void stepAnimation(float f, float h, float i) {
        final var openness = (Mth.sin(f * 0.02F) * 0.1F + 1.25F) * i;
        leftLid.yRot = 3.1415927F + openness;
        rightLid.yRot = -openness;

        leftPages.yRot = openness;
        rightPages.yRot = -openness;

        flipPage.yRot = openness - openness * 2.0F * h;

        leftPages.x = Mth.sin(openness);
        rightPages.x = Mth.sin(openness);

        flipPage.x = Mth.sin(openness);
    }

    public void setAnimation(float f, float h, float i) {
        stepAnimation(f, h, i);
    }

    public void step(float tick) {
        stepFlipLeft();
        stepFlipRight();
        stepOpen();
        stepClose();

        if (flip == 0f) {
            stepAnimation(0, 0, open);
        } else {
            stepAnimation(0, ((Mth.lerp(tick, oldFlip, flip) + 0.75F) - (float) Mth.floor(flip + 0.75f)) * 1.6F - 0.3F, open);
        }
    }

    public void stepWithItem(SpellBookItem book, ItemStack item, float tick) {
        stepFlipLeftWithItem(book, item);
        stepFlipLeft();
        stepFlipRightWithItem(book, item);
        stepFlipRight();
        stepOpenWithItem(book, item);
        stepOpen();

        if (flip == 0f) {
            stepAnimation(0, 0, open);
        } else {
            stepAnimation(0, ((Mth.lerp(tick, oldFlip, flip) + 0.75F) - (float) Mth.floor(flip + 0.75f)) * 1.6F - 0.3F, open);
        }
    }

    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int i, int j, float f, float g, float h, float k) {
        root.render(matrices, vertices, i, j, f, g, h, k);
    }
}
