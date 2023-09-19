package mikolmisol.spellcraft.parts.foci;

import mikolmisol.spellcraft.parts.Part;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public interface Focus extends Part {
    void modifySpellPriorToCasting();

    void tickSpellCast();

    @NotNull Component getDescription();
}
