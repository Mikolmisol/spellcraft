package mikolmisol.spellcraft.spells.effects.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.damage.SpellcraftDamageSources;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.LightningDamageAttribute;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.RequiredTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Lightning implements Effect {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "lightning");

    private static final double BASE_COST = 20;

    private static final float BASE_DAMAGE = 5;

    private static final RequiredTargets REQUIRED_TARGETS = RequiredTargets.atLeastOneOf(SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static final Component NAME = Component.translatable("effect.spellcraft.lightning");

    private static final int DECIMAL_COLOR = FastColor.ARGB32.color(255, 100, 0, 255);

    private static void powerLightningRod(Level level, BlockPos position) {
        final var state = level.getBlockState(position);
        if (state.is(Blocks.LIGHTNING_ROD)) {
            ((LightningRodBlock) state.getBlock()).onLightningStrike(state, level, position);
        }
    }

    private static Optional<BlockPos> randomStepCleaningCopper(Level level, BlockPos blockPos) {
        final var struckPositions = BlockPos.randomInCube(level.random, 10, blockPos, 1).iterator();

        var struckPosition = (BlockPos) null;
        var struckState = (BlockState) null;

        do {
            if (!struckPositions.hasNext()) {
                return Optional.empty();
            }

            struckPosition = struckPositions.next();
            struckState = level.getBlockState(struckPosition);

        } while (!(struckState.getBlock() instanceof WeatheringCopper));

        final var shuttupJava = struckPosition;
        WeatheringCopper.getPrevious(struckState).ifPresent((previousState) -> level.setBlockAndUpdate(shuttupJava, previousState));
        level.levelEvent(3002, struckPosition, -1);
        return Optional.of(struckPosition);
    }

    private static void randomWalkCleaningCopper(Level level, BlockPos newPosition, BlockPos.MutableBlockPos randomWalkPosition, int i) {
        randomWalkPosition.set(newPosition);

        for (int j = 0; j < i; ++j) {
            Optional<BlockPos> optional = randomStepCleaningCopper(level, randomWalkPosition);
            if (optional.isEmpty()) {
                break;
            }

            randomWalkPosition.set(optional.get());
        }
    }

    private static void clearCopperOnLightningStrike(Level level, BlockPos position) {
        final var struckState = level.getBlockState(position);
        var lightningRodRedirectedPosition = (BlockPos) null;
        var lightningRodRedirectedState = (BlockState) null;

        if (struckState.is(Blocks.LIGHTNING_ROD)) {
            lightningRodRedirectedPosition = position.relative(struckState.getValue(LightningRodBlock.FACING).getOpposite());
            lightningRodRedirectedState = level.getBlockState(lightningRodRedirectedPosition);

        } else {
            lightningRodRedirectedPosition = position;
            lightningRodRedirectedState = struckState;
        }

        if (lightningRodRedirectedState.getBlock() instanceof WeatheringCopper) {
            level.setBlockAndUpdate(lightningRodRedirectedPosition, WeatheringCopper.getFirst(level.getBlockState(lightningRodRedirectedPosition)));
            final var walkingPosition = position.mutable();

            var randomWalk = level.random.nextInt(3) + 3;

            for (int j = 0; j < randomWalk; ++j) {
                int k = level.random.nextInt(8) + 1;
                randomWalkCleaningCopper(level, lightningRodRedirectedPosition, walkingPosition, k);
            }
        }
    }

    @Override
    public void cast(Spell spell, Caster caster, Targets targets, Level level) {
        var success = false;

        final var attributes = spell.getShape().getAttributes();

        final var damageAttribute = attributes.get(SpellcraftAttributeTypes.LIGHTNING_DAMAGE);
        final var damage = damageAttribute.getDamage();

        for (final var entityTarget : targets.getAllOfType(SpellcraftTargetTypes.ENTITY)) {
            final var entity = entityTarget.getValue();

            if (level.isClientSide) {

            } else {
                final var damageSources = SpellcraftDamageSources.of(entity);

                var armorMultiplier = 1.0f;
                if (entity instanceof LivingEntity livingEntity) {
                    final var armor = livingEntity.getArmorValue();
                    armorMultiplier = (float) Math.pow(1.1f, armor);
                }

                entity.hurt(
                        damageSources.spellcraft_lightning(
                                spell,
                                caster.asEntity(),
                                entity

                        ),
                        damage * armorMultiplier
                );

                spell.playSuccessSound(caster, level);
            }
        }

        for (final var blockTarget : targets.getAllOfType(SpellcraftTargetTypes.BLOCK)) {
            final var block = blockTarget.getValue();

            if (level.isClientSide) {

            } else {
                powerLightningRod(level, block.position());

                if (Mth.nextFloat(level.random, 0, 1) > 0.75) {
                    clearCopperOnLightningStrike(level, block.position());
                }
            }
        }

        if (!success) {
            spell.playFailureSound(caster, level);
        }
    }

    @Override
    public @NotNull Component getName() {
        return NAME;
    }

    @Override
    public int getDecimalColor() {
        return DECIMAL_COLOR;
    }

    @Override
    public @NotNull RequiredTargets getRequiredTargets() {
        return REQUIRED_TARGETS;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public double getCost() {
        return BASE_COST;
    }

    @Override
    public void defineBaseAttributes(@NotNull Attributes attributes) {
        attributes.create(SpellcraftAttributeTypes.LIGHTNING_DAMAGE, new LightningDamageAttribute(BASE_DAMAGE));
    }
}
