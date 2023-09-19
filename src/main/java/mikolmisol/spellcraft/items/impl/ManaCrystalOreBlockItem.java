package mikolmisol.spellcraft.items.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static mikolmisol.spellcraft.blocks.ore.ManaCrystalOreBlock.RICHNESS;

public final class ManaCrystalOreBlockItem extends BlockItem {
    private static final String TAG_RICHNESS = "Richness";

    public ManaCrystalOreBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public int getRichness(ItemStack item) {
        final var tag = item.getTag();

        if (tag == null) {
            return 0;
        }

        return tag.getInt(TAG_RICHNESS);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos position, Level level, @Nullable Player player, ItemStack item, BlockState block) {
        final var server = level.getServer();

        if (server == null) {
            return false;
        }

        final var tag = getBlockEntityData(item);

        if (tag == null) {
            return false;
        }

        final var ore = level.getBlockEntity(position);

        if (ore == null) {
            return false;
        }

        if (!level.isClientSide && ore.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
            return false;
        }

        final var tagWithoutMetadata = ore.saveWithoutMetadata();
        final var tagWithoutMetadataCopy = tagWithoutMetadata.copy();

        tagWithoutMetadata.merge(tag);

        if (!tagWithoutMetadata.equals(tagWithoutMetadataCopy)) {
            ore.load(tagWithoutMetadata);
            ore.setChanged();
            return true;
        }

        final var normalTag = item.getOrCreateTag();

        normalTag.putInt(TAG_RICHNESS, block.getValue(RICHNESS));

        return false;
    }
}
