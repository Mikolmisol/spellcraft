package mikolmisol.spellcraft.spells.modifiers.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class Massless implements Modifier {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "massless");

    @Override
    public @NotNull Component getName() {
        return Component.translatable("modifier.spellcraft.massless");
    }

    @Override
    public int getMaximumStackSize() {
        return 3;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

}
