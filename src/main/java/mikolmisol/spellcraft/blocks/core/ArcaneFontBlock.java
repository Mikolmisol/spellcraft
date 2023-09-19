package mikolmisol.spellcraft.blocks.core;

import com.google.common.collect.ImmutableMap;
import mikolmisol.spellcraft.block_entities.font.ArcaneFontBlockEntity;
import mikolmisol.spellcraft.blocks.SpellcraftBlockWithBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ArcaneFontBlock extends SpellcraftBlockWithBlockEntity {
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);

    private static final VoxelShape SHAPE;

    public ArcaneFontBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LIGHT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }

        if (player.isSecondaryUseActive()) {
            return InteractionResult.CONSUME;
        }

        final var blockEntity = level.getBlockEntity(position);

        if (!(blockEntity instanceof final ArcaneFontBlockEntity font)) {
            return InteractionResult.CONSUME;
        }

        if (FluidStorageUtil.interactWithFluidStorage(font.getFluidStorageForSpellCrafting(), player, hand)) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArcaneFontBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    public static int getLightLevelForState(BlockState state) {
        return state.getValue(LIGHT_LEVEL);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    static {
        SHAPE = Shapes.join(
                Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.19, 0.9375),
                Shapes.join(
                        Shapes.box(0.185, 0, 0.185, 0.815, 0.315, 0.815),
                        Shapes.join(
                                Shapes.box(0.25, 0, 0.25, 0.75, 0.7, 0.75),
                                Shapes.join(
                                        Shapes.box(0.065, 0.625, 0.065, 0.935, 0.94, 0.935),
                                        Shapes.box(0.185, 0.8, 0.185, 0.815, 0.94, 0.815),
                                        BooleanOp.OR
                                ),
                                BooleanOp.NOT_SAME
                        ),
                        BooleanOp.NOT_SAME
                ),
                BooleanOp.NOT_SAME
        );
    }
}
