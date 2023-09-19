package mikolmisol.spellcraft.block_entities.catalyst;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.storage.WorldlyManaStorage;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneCatalystBlockEntity extends BlockEntity implements WorldlyManaStorage, MenuProvider {

    private static final Component NAME;

    private ManaStorage mana = ManaStorage.creative();

    public ArcaneCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.ARCANE_BRAZIER, blockPos, blockState);
    }

    @Override
    public @Nullable ManaStorage getManaStorageForDirection(@NotNull Direction direction) {
        return mana;
    }

    @Override
    public @Nullable ManaStorage getManaStorage() {
        return mana;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        mana.toTag(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        mana = ManaStorage.fromTag(tag);
    }

    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int synchronisationIdentifier, Inventory inventory, Player player) {
        return null;
    }

    static {
        NAME = TextUtil.translate(
                String.format("gui.%s.arcane_catalyst", Spellcraft.MOD_ID),
                "Arcane Catalyst"
        );
    }
}
