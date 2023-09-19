package mikolmisol.spellcraft.block_entities.container;

import net.minecraft.world.inventory.CraftingContainer;

public interface SimpleImplementedCraftingContainer extends CraftingContainer, SimpleImplementedContainer {
    int WIDTH = 3;

    int HEIGHT = 3;

    @Override
    default int getWidth() {
        return WIDTH;
    }

    @Override
    default int getHeight() {
        return HEIGHT;
    }
}
