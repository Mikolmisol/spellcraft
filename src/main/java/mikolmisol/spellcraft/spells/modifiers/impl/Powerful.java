package mikolmisol.spellcraft.spells.modifiers.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class Powerful implements Modifier {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "powerful");

    private static final int MAXIMUM_STACK_SIZE = 5;

    @Override
    public @NotNull Component getName() {
        return Component.literal("Powerful");
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
