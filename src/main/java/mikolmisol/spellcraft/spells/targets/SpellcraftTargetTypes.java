package mikolmisol.spellcraft.spells.targets;

import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.targets.impl.BlockTarget;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

public final class SpellcraftTargetTypes {

    public static final TargetType<ItemStack> ITEM;

    public static final TargetType<Entity> ENTITY;

    public static final TargetType<BlockTarget> BLOCK;

    static {
        final var itemIdentifier = new ResourceLocation(MOD_ID, "item");
        ITEM = Registry.register(SpellcraftRegistries.TARGET_TYPE, itemIdentifier, new TargetType(itemIdentifier));

        final var entityIdentifier = new ResourceLocation(MOD_ID, "entity");
        ENTITY = Registry.register(SpellcraftRegistries.TARGET_TYPE, entityIdentifier, new TargetType(entityIdentifier));

        final var blockIdentifier = new ResourceLocation(MOD_ID, "block");
        BLOCK = Registry.register(SpellcraftRegistries.TARGET_TYPE, blockIdentifier, new TargetType(blockIdentifier));
    }

    private SpellcraftTargetTypes() {
    }
}
