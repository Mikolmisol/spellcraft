package mikolmisol.spellcraft.blocks;

import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.entities.impl.ManaSpark;
import mikolmisol.spellcraft.items.SpellcraftItems;
import mikolmisol.spellcraft.spells.impl.ManaCrystalCaster;
import mikolmisol.spellcraft.util.GeomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public interface ExplodingBlock {
    static boolean isReckless(Entity entity) {
        if (entity instanceof Player player && player.isCrouching()) {
            return false;
        } else if (entity instanceof ItemEntity item) {
            return !item.getItem().is(SpellcraftItems.MANA_BULB);
        } else {
            return !(entity instanceof Bat) && !(entity instanceof Cat) && (!(entity instanceof Wolf wolf && wolf.isTame()));
        }
    }

    default void explode(@Nullable Entity trampler, Level level, BlockPos pos) {
        if (trampler != null) {
            if (!isReckless(trampler)) {
                return;
            }
        }

        level.playSound(null, pos, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.BLOCKS, 1f, 1f);
        if (level.isClientSide) {
            return;
        }

        final var spark = new ManaSpark(SpellcraftEntityTypes.MANA_SPARK, level);

        spark.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        level.addFreshEntity(spark);

        final var diameter = getExplosionDiameter(level, pos);

        final var area = new AABB(
                pos.getX() - diameter, pos.getY() - diameter, pos.getZ() - diameter,
                pos.getX() + diameter, pos.getY() + diameter, pos.getZ() + diameter
        );

        final var mockCaster = new ManaCrystalCaster(pos);

        level.playSound(null, pos, SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS, 15f, 1f);

        level.removeBlock(pos, false);

        final var blockPositions = GeomUtil.getAllBlockPositions(area);

        for (final var blockPos : blockPositions) {
            final var blockState = level.getBlockState(blockPos);
            if (blockState.getBlock() instanceof ExplodingBlock explodingBlock) {
                explodingBlock.explode(null, level, blockPos);
            }
        }
    }

    double getExplosionDiameter(Level level, BlockPos pos);

    int getExplosionPowerMultipliers(Level level, BlockPos pos);

    int getExplosionDurationMultipliers(Level level, BlockPos pos);
}
