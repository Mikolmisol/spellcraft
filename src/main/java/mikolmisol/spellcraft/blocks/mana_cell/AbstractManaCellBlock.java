package mikolmisol.spellcraft.blocks.mana_cell;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;

public abstract class AbstractManaCellBlock extends Block implements EntityBlock {
    public AbstractManaCellBlock(Properties properties) {
        super(properties);
    }
}
