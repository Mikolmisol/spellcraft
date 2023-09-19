package mikolmisol.spellcraft.item_renderers;

import mikolmisol.spellcraft.items.SpellcraftItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public final class SpellcraftItemRenderers {

    private SpellcraftItemRenderers() {
    }

    public static void initialise() {
        BuiltinItemRendererRegistry.INSTANCE.register(mikolmisol.spellcraft.items.SpellcraftItems.MANA_CRYSTAL_CLUSTER, new ManaCrystalItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(mikolmisol.spellcraft.items.SpellcraftItems.MANA_SHARD, new ManaShardItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(mikolmisol.spellcraft.items.SpellcraftItems.ENCHANTED_BROOM, new EnchantedBroomItemRenderer());
    }
}
