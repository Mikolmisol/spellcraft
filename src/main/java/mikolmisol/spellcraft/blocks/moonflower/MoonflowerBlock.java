package mikolmisol.spellcraft.blocks.moonflower;

import mikolmisol.spellcraft.blocks.ExplodingBlock;
import mikolmisol.spellcraft.items.SpellcraftItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class MoonflowerBlock extends CropBlock implements ExplodingBlock {
    public MoonflowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState block, Level level, BlockPos position, RandomSource random) {
        super.animateTick(block, level, position, random);
    }

    @Override
    public void randomTick(BlockState block, ServerLevel level, BlockPos position, RandomSource random) {
        if (!level.isNight()) {
            return;
        }

        if (!level.canSeeSky(position)) {
            return;
        }

        final var age = getAge(block);

        if (age >= getMaxAge()) {
            return;
        }

        final var moonBrightness = level.getMoonBrightness();

        final var growthMultiplier = getGrowthSpeed(this, level, position) * moonBrightness;

        if (random.nextInt((int) (25.0F / growthMultiplier) + 1) == 0) {
            level.setBlock(position, getStateForAge(age + 1), 2);
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SpellcraftItems.MANA_BULB;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        explode(entity, level, pos);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        explode(entity, level, pos);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        explode(entity, level, pos);
    }

    @Override
    public void wasExploded(Level level, BlockPos blockPos, Explosion explosion) {
        explode(null, level, blockPos);
    }

    @Override
    public double getExplosionDiameter(Level level, BlockPos pos) {
        return (int) Math.ceil(getAge(level.getBlockState(pos)) / 2.5);
    }

    @Override
    public int getExplosionPowerMultipliers(Level level, BlockPos pos) {
        return (int) Math.ceil(getAge(level.getBlockState(pos)) / 2.5);
    }

    @Override
    public int getExplosionDurationMultipliers(Level level, BlockPos pos) {
        return (int) Math.ceil(getAge(level.getBlockState(pos)) / 5.0);
    }
}
