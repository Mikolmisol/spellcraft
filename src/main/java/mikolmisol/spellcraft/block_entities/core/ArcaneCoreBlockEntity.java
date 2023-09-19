package mikolmisol.spellcraft.block_entities.core;

import com.google.common.collect.Sets;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntity;
import mikolmisol.spellcraft.block_entities.SpellcraftBlockEntityTypes;
import mikolmisol.spellcraft.block_entities.barrier.ArcaneHoldingCell;
import mikolmisol.spellcraft.block_entities.font.ArcaneFont;
import mikolmisol.spellcraft.block_entities.pedestal.ArcanePedestal;
import mikolmisol.spellcraft.util.GeomUtil;
import mikolmisol.spellcraft.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public final class ArcaneCoreBlockEntity extends SpellcraftBlockEntity implements MenuProvider {
    private static final Component NAME;

    private static final int SEARCH_COOLDOWN = 100;

    private final Set<ArcaneCorePedestalSlotData> pedestals = Sets.newHashSet();

    private final Set<ArcaneCoreFontSlotData> fonts = Sets.newHashSet();

    private final Set<ArcaneCoreHoldingCellSlotData> cells = Sets.newHashSet();

    private int ticksSinceLastSearch;

    private final AABB searchArea;

    public ArcaneCoreBlockEntity(BlockPos position, BlockState block) {
        super(SpellcraftBlockEntityTypes.ARCANE_CORE, position, block);
        this.searchArea = new AABB(
                position.north(5).west(5).below(5),
                position.south(5).east(5).above(5)
        );
    }

    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }


    public static void tick(Level level, BlockPos position, BlockState block, ArcaneCoreBlockEntity core) {
        core.ticksSinceLastSearch += 1;

        if (core.ticksSinceLastSearch < SEARCH_COOLDOWN) {
            return;
        }

        core.ticksSinceLastSearch = 0;

        core.pedestals.clear();
        core.fonts.clear();
        core.cells.clear();

        final var positionsInLineOfSight = GeomUtil.getAllBlockPositionsInLineOfSight(
                core.searchArea,
                level,
                position
        );

        for (final var positionInLineOfSight : positionsInLineOfSight) {
            final var blockEntityInLineOfSight = level.getBlockEntity(positionInLineOfSight);

            if (blockEntityInLineOfSight instanceof ArcanePedestal pedestal) {
                core.pedestals.add(new ArcaneCorePedestalSlotData(
                        ArcaneCoreSlotDataType.INPUT,
                        pedestal
                ));
            }

            if (blockEntityInLineOfSight instanceof ArcaneFont font) {
                core.fonts.add(new ArcaneCoreFontSlotData(
                        ArcaneCoreSlotDataType.INPUT,
                        font
                ));
            }

            if (blockEntityInLineOfSight instanceof ArcaneHoldingCell cell) {
                core.cells.add(new ArcaneCoreHoldingCellSlotData(
                        ArcaneCoreSlotDataType.INPUT,
                        cell
                ));
            }
        }
    }

    static {
        NAME = TextUtil.translate(
                String.format("gui.%s.arcane_core", Spellcraft.MOD_ID),
                "Arcane Core"
        );
    }
}
