package mikolmisol.spellcraft.spells.modifiers.impl;

import com.google.common.collect.Sets;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.shapes.SpellcraftShapes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class Distant implements Modifier {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "distant");

    private static final int MAXIMUM_STACK_SIZE = 5;

    private static final Set<Shape> VALID_SHAPES = Sets.newHashSet(SpellcraftShapes.BOLT, SpellcraftShapes.NOVA);

    @Override
    public @NotNull Component getName() {
        return Component.literal("Distant");
    }

    @Override
    public int getMaximumStackSize() {
        return MAXIMUM_STACK_SIZE;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

}
