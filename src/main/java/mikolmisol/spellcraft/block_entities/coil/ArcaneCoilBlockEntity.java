package mikolmisol.spellcraft.block_entities.coil;

import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.storage.WorldlyManaStorage;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.blocks.coil.ArcaneCoilBlock;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneCoilBlockEntity extends SpellcraftBlockEntity implements ArcaneCoil, WorldlyManaStorage, Caster {

    private final @NotNull Vec3 castingPosition;
    private ManaStorage mana = ManaStorage.of(100);
    private @Nullable Spell spell;

    private @Nullable SpellCastEvent spellCastEvent;

    public ArcaneCoilBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SpellcraftBlockEntityTypes.ARCANE_COIL, blockPos, blockState);
        this.castingPosition = Vec3.atCenterOf(blockPos).add(0, 0.5, 0);
    }

    public static void tick(Level level, BlockPos position, BlockState block, ArcaneCoilBlockEntity coil) {
        if (!block.getValue(ArcaneCoilBlock.TOP)) {
            return;
        }

        if (level.getBestNeighborSignal(position.below()) == 0) {
            return;
        }

        if (coil.spell == null) {
            return;
        }

        if (!coil.spellcraft_isCasting()) {
            coil.spellcraft_startCasting(coil.spell, level);
        }
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
    public @NotNull Vec3 getSpellCastingPosition() {
        return Vec3.atCenterOf(getBlockPos());
    }

    @Override
    public void spellcraft_startCasting(@NotNull Spell spell, @NotNull Level level) {
        spellCastEvent = spell.getSpellCastEventConstructor().create(spell, this, level);
    }

    @Override
    public boolean spellcraft_isCasting() {
        return spellCastEvent != null;
    }

    @Override
    public void spellcraft_stopCasting() {
        if (spellCastEvent != null) {
            spellCastEvent.failSpellCast();
        }
    }

    @Override
    public void spellcraft_removeSpellCastEventReference() {
        spellCastEvent = null;
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
    public @NotNull BlockPos getArcaneCoilTipPosition() {
        return getBlockPos();
    }
}
