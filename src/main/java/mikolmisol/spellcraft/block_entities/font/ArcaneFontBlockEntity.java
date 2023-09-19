package mikolmisol.spellcraft.block_entities.font;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.storage.WorldlyFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static mikolmisol.spellcraft.blocks.core.ArcaneFontBlock.LIGHT_LEVEL;

public class ArcaneFontBlockEntity extends SpellcraftBlockEntity implements ArcaneFont, WorldlyFluidStorage {
    public static final long CAPACITY = FluidConstants.BUCKET;

    private final SingleFluidStorage storage = SingleFluidStorage.withFixedCapacity(CAPACITY, this::setChanged);

    public ArcaneFontBlockEntity(BlockPos pos, BlockState state) {
        super(SpellcraftBlockEntityTypes.ARCANE_FONT, pos, state);
    }

    @Override
    public @NotNull Storage<FluidVariant> getFluidStorageForSpellCrafting() {
        return storage;
    }

    @Override
    public @Nullable Storage<FluidVariant> getFluidStorageForDirection(Direction direction) {
        return storage;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        storage.writeNbt(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        storage.readNbt(tag);
    }

    @Override
    public void setChanged() {
        final var fluid = StorageUtil.findStoredResource(storage);

        if (fluid != null) {
            level.setBlockAndUpdate(
                    getBlockPos(),
                    getBlockState().setValue(
                            LIGHT_LEVEL,
                            fluid.getFluid().defaultFluidState().createLegacyBlock().getLightEmission()
                    )
            );
        }
        else {
            level.setBlockAndUpdate(
                    getBlockPos(),
                    getBlockState().setValue(
                            LIGHT_LEVEL,
                            0
                    )
            );
        }

        super.setChanged();
        sendUpdateToClients();
    }
}
