package mikolmisol.spellcraft.blocks.ore;

import mikolmisol.spellcraft.block_entities.ore.ManaCrystalOreBlockEntity;
import mikolmisol.spellcraft.items.impl.ManaCrystalOreBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class ManaCrystalOreBlock extends BaseEntityBlock {
    public static final IntegerProperty RICHNESS = IntegerProperty.create("richness", 0, 2);

    public ManaCrystalOreBlock(Properties properties) {
        super(properties.lightLevel(ManaCrystalOreBlock::getLightLevelForState));
        registerDefaultState(defaultBlockState().setValue(RICHNESS, 0));
    }

    private static int getLightLevelForState(BlockState block) {
        return switch (block.getValue(RICHNESS)) {
            case 0 -> 7;
            case 1 -> 9;
            default -> 11;
        };
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ManaCrystalOreBlockEntity(position, block);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RICHNESS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext place) {
        final var item = place.getItemInHand();
        if (item.getItem() instanceof ManaCrystalOreBlockItem manaCrystalOreItem) {
            final var richness = manaCrystalOreItem.getRichness(item);
            return defaultBlockState().setValue(RICHNESS, richness);
        }

        return defaultBlockState();
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
