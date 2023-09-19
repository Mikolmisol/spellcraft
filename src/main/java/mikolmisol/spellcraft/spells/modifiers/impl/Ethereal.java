package mikolmisol.spellcraft.spells.modifiers.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class Ethereal implements Modifier {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "ethereal");

    @Override
    public @NotNull Component getName() {
        return Component.translatable("modifier.spellcraft.ethereal");
    }

    @Override
    public int getMaximumStackSize() {
        return 1;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }
}
