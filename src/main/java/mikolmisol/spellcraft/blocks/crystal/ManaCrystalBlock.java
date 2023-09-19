package mikolmisol.spellcraft.blocks.crystal;

import mikolmisol.spellcraft.block_entities.crystal.ManaCrystalBlockEntity;
import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.entities.impl.ManaSpark;
import mikolmisol.spellcraft.spells.impl.ManaCrystalCaster;
import mikolmisol.spellcraft.util.GeomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ManaCrystalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final VoxelShape SHAPE_UP = Shapes.box(0.3, 0, 0.3, 0.7, 0.75, 0.7);

    private static final VoxelShape SHAPE_DOWN = Shapes.box(0.3, 0.25, 0.3, 0.7, 1, 0.7);

    private static final VoxelShape SHAPE_NORTH = Shapes.box(0.3, 0.3, 0.3, 0.7, 0.75, 1);

    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0.3, 0.3, 0, 0.7, 0.75, 0.7);

    private static final VoxelShape SHAPE_EAST = Shapes.box(0, 0.3, 0.3, 0.75, 0.75, 0.7);

    private static final VoxelShape SHAPE_WEST = Shapes.box(0.3, 0.3, 0.3, 1, 0.75, 0.7);

    public ManaCrystalBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    private static boolean isReckless(Entity entity) {
        if (entity instanceof Player player && player.isCrouching()) {
            return false;
        } else {
            return !(entity instanceof Bat) && !(entity instanceof Cat) && (!(entity instanceof TamableAnimal pet && pet.isTame()));
        }
    }

    private void explode(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.BLOCKS, 1f, 1f);
        if (!level.isClientSide) {
            final var spark = new ManaSpark(SpellcraftEntityTypes.MANA_SPARK, level);

            spark.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            level.addFreshEntity(spark);
            level.removeBlock(pos, false);

            final var volume = new AABB(
                    pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10,
                    pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10
            );

            final var mockCaster = new ManaCrystalCaster(pos);

            /*
            final var effect = Lists.newArrayList(SpellcraftRegistry.getRandomDamagingSpellEffect());

            if (level.random.nextInt(0, 10) > 7) {
                effect.add(SpellcraftRegistry.getRandomDamagingSpellEffect());
            }

            final var modifiers = Lists.newArrayList(SpellcraftModifiers.POWER);

            if (level.getBlockEntity(pos) instanceof ManaCrystalBlockEntity crystal) {
                final var size = crystal.getSize();
                final var power = size / 2.0;
                final var duration = size / 4.0;

                for (var index = 0; index != power; index += 1) {
                    modifiers.add(SpellcraftModifiers.POWER);
                }

                for (var index = 0; index != duration; index += 1) {
                    modifiers.add(SpellcraftModifiers.DURATION);
                }
            }

            final var spell = Spell.of(
                    SpellcraftShapes.NOVA,
                    effect,
                    modifiers
            );

            spell.tickCast(mockCaster, level);
             */

            final var blockPositions = GeomUtil.getAllBlockPositions(volume);

            for (final var blockPos : blockPositions) {
                final var blockState = level.getBlockState(blockPos);
                if (blockState.getBlock() instanceof ManaCrystalBlock manaCrystal) {
                    manaCrystal.explode(level, blockPos);
                }
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (isReckless(entity)) {
            explode(level, pos);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        if (isReckless(entity)) {
            explode(level, pos);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (isReckless(entity)) {
            explode(level, pos);
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos blockPos, Explosion explosion) {
        explode(level, blockPos);
    }

    @Override
    public boolean canSurvive(BlockState block, LevelReader level, BlockPos position) {
        final var direction = block.getValue(FACING);
        final var supportBlockPosition = position.relative(direction.getOpposite());
        return level.getBlockState(supportBlockPosition).isFaceSturdy(level, supportBlockPosition, direction);
    }

    @Override
    public BlockState updateShape(BlockState block, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos position, BlockPos blockPos2) {
        if (block.getValue(WATERLOGGED)) {
            level.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction == block.getValue(FACING).getOpposite() && !canSurvive(block, level, position)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(block, direction, blockState2, level, position, blockPos2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext place) {
        final var level = place.getLevel();
        final var position = place.getClickedPos();
        return defaultBlockState()
                .setValue(WATERLOGGED, level.getFluidState(position).getType() == Fluids.WATER)
                .setValue(FACING, place.getClickedFace());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collision) {
        return switch (state.getValue(FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState block) {
        return new ManaCrystalBlockEntity(position, block);
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos blockPos, BlockState blockState) {
    }
}
