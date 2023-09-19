package mikolmisol.spellcraft.block_entities.launcher;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.blocks.launcher.ArcaneLauncherBlock;
import mikolmisol.spellcraft.entities.impl.SpellProjectile;
import mikolmisol.spellcraft.items.SpellContainingItem;
import mikolmisol.spellcraft.mana.ManaStorage;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.casting.SpellCastEvent;
import mikolmisol.spellcraft.spells.targets.Target;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ArcaneLauncherBlockEntity extends BlockEntity implements Caster {
    private static final String TAG_ANGLE = "Angle";
    private static final float MAXIMUM_ANGLE = 90;

    private ManaStorage mana = ManaStorage.creative();

    private float angle;

    private @Nullable SpellCastEvent spellCastEvent;

    public ArcaneLauncherBlockEntity(BlockPos position, BlockState block) {
        super(SpellcraftBlockEntityTypes.ARCANE_CANNON, position, block);
    }

    public void fireProjectile(Level level) {

        /*
        final var projectile = SpellProjectile.create(level, this, spell, hit -> {
            var entityHit = (LivingEntity) null;
            var blockPosHit = (BlockPos) null;
            var directionHit = (Direction) null;

            if (hit instanceof EntityHitResult entityHitResult) {
                if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                    entityHit = livingEntity;
                }
            } else if (hit instanceof BlockHitResult blockHitResult) {
                blockPosHit = blockHitResult.getBlockPos();
                directionHit = blockHitResult.getDirection();
            }

            final var targets = Lists.<Target<?>>newArrayList();

            if (blockPosHit != null) {
                targets.add(Target.ofBlock(blockPosHit, directionHit));
            }

            if (entityHit != null) {
                targets.add(Target.ofEntity(entityHit));
            }
        });

        prepareProjectile(level, projectile);
        level.addFreshEntity(projectile);
         */
    }

    private void prepareProjectile(final @NotNull Level level, final @NotNull SpellProjectile projectile) {
        final var cannonPosition = getBlockPos();
        final var cannonBlock = level.getBlockState(cannonPosition);
        final var centerPosition = Vec3.atCenterOf(cannonPosition);
        final var cannonDirection = cannonBlock.getValue(ArcaneLauncherBlock.FACING);
        final var firingPosition = getFiringPosition(centerPosition, cannonDirection);

        projectile.setPos(firingPosition);
        projectile.shoot(cannonDirection.getStepX(), cannonDirection.getStepY() * (angle / 90f), cannonDirection.getStepZ(), 2f, 0.1f);
    }

    private Vec3 getFiringPosition(final @NotNull Vec3 position, final @NotNull Direction direction) {
        double x = position.x + 0.7 * direction.getStepX();
        double z = position.z + 0.7 * direction.getStepZ();
        return new Vec3(x, position.y, z);
    }

    public void incrementAngle() {
        angle = Math.min(angle + 1, MAXIMUM_ANGLE);
    }

    public void decrementAngle() {
        angle = Math.max(angle - 1, 0);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putFloat(TAG_ANGLE, angle);
        mana.toTag(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        angle = tag.getFloat(TAG_ANGLE);
        mana = ManaStorage.fromTag(tag);
    }

    @Override
    public @NotNull ManaStorage getManaStorage() {
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
}
