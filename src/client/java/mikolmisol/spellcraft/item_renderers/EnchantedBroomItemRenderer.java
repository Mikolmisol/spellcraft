package mikolmisol.spellcraft.item_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import mikolmisol.spellcraft.items.impl.ArcaneBroomItem;
import mikolmisol.spellcraft.render_layers.SpellcraftRenderLayers;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class EnchantedBroomItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack item, ItemDisplayContext transform, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        if (!(item.getItem() instanceof ArcaneBroomItem broom)) {
            return;
        }

        final var knob = broom.getKnob(item);

        if (knob == null) {
            return;
        }

        final var brush = broom.getBrush(item);

        if (brush == null) {
            return;
        }

        final var rod = broom.getRod(item);

        if (rod == null) {
            return;
        }
    }
}
