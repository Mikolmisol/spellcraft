package mikolmisol.spellcraft.block_entities.ore;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.blocks.ore.ManaCrystalOreBlock;
import mikolmisol.spellcraft.render_data_types.BipyramidalCrystal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class ManaCrystalOreBlockEntity extends BlockEntity {
    private BipyramidalCrystal[] crystals;

    public ManaCrystalOreBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.MANA_CRYSTAL_ORE, blockPos, blockState);
    }

    public BipyramidalCrystal[] getCrystals() {
        if (crystals == null) {
            final var richness = getBlockState().getValue(ManaCrystalOreBlock.RICHNESS);
            crystals = BipyramidalCrystal.getRandomCrystalsForRichness(richness);
        }

        return crystals;
    }
}
