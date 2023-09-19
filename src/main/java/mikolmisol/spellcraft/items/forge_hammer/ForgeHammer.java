package mikolmisol.spellcraft.items.forge_hammer;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.util.GeomUtil;
import mikolmisol.spellcraft.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ForgeHammer extends TieredItem {

    private static final double RANGE = 3;

    private static final String HAS_HEAT = "HasHeat";

    private static final String HAS_ANVIL = "HasAnvil";

    public static final Component TOOLTIP_READY_TO_USE;

    public static final Component TOOLTIP_MISSING_HEAT;

    public static final Component TOOLTIP_MISSING_ANVIL;

    public ForgeHammer(Properties properties) {
        super(Tiers.IRON, properties);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
            final var randomSource = RandomSource.create();
            final var moreDamaged = stack.copy();

            final var unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack);

            if (!DigDurabilityEnchantment.shouldIgnoreDurabilityDrop(stack, unbreakingLevel, randomSource)) {
                moreDamaged.setDamageValue(stack.getDamageValue() + 1);
            }

            return moreDamaged;
	    }

	    return ItemStack.EMPTY;
    }

    @Override
    public void inventoryTick(ItemStack item, Level level, Entity entity, int i, boolean bl) {
        final var tag = item.getOrCreateTag();
        tag.putBoolean(HAS_HEAT, false);
        tag.putBoolean(HAS_ANVIL, false);

        final var area = new AABB(
                entity.position().subtract(RANGE, RANGE, RANGE),
                entity.position().add(RANGE, RANGE, RANGE)
        );

        final var positions = GeomUtil.getAllBlockPositions(area);

        var hasHeat = false;
        var hasAnvil = false;

        for (final var position : positions) {
            if (hasHeat && hasAnvil) {
                return;
            }

            final var block = level.getBlockState(position);

            if (!hasHeat) {
                if (block.getBlock() == Blocks.MAGMA_BLOCK
                        || block.getBlock() == Blocks.LAVA
                        || (block.getBlock() == Blocks.BLAST_FURNACE && block.getValue(BlastFurnaceBlock.LIT))) {

                    hasHeat = true;
                    tag.putBoolean(HAS_HEAT, true);
                    continue;
                }

                final var fluid = level.getFluidState(position);

                if (fluid.getType() == Fluids.LAVA
                        || fluid.getType() == Fluids.FLOWING_LAVA) {

                    hasHeat = true;
                    tag.putBoolean(HAS_HEAT, true);
                    continue;
                }
            }

            if (!hasAnvil) {
                if (block.getBlock() == Blocks.ANVIL
                        || block.getBlock() == Blocks.CHIPPED_ANVIL
                        || block.getBlock() == Blocks.DAMAGED_ANVIL) {

                    hasAnvil = true;
                    tag.putBoolean(HAS_ANVIL, true);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        final var tag = item.getTag();

        if (tag == null) {
            return;
        }

        final var hasHeat = tag.getBoolean(HAS_HEAT);
        final var hasAnvil = tag.getBoolean(HAS_ANVIL);

        if (hasHeat && hasAnvil) {
            list.add(TOOLTIP_READY_TO_USE);
            return;
        }

        if (!hasHeat) {
            list.add(TOOLTIP_MISSING_HEAT);
        }

        if (!hasAnvil) {
            list.add(TOOLTIP_MISSING_ANVIL);
        }
    }

    static {
        TOOLTIP_READY_TO_USE = TextUtil.translateAndEdit(
                "tooltip."+Spellcraft.MOD_ID+".forge_hammer.ready",
                "Ready to use!",
                component -> {
                    component.withStyle(ChatFormatting.GREEN);
                    component.withStyle(ChatFormatting.ITALIC);
                }
        );

        TOOLTIP_MISSING_HEAT = TextUtil.translateAndEdit(
                "tooltip."+Spellcraft.MOD_ID+".forge_hammer.missing_heat",
                "Requires: Lava, a Magma Block, or a smelting Blast Furnace within 3 blocks.",
                component -> {
                    component.withStyle(ChatFormatting.RED);
                    component.withStyle(ChatFormatting.ITALIC);
                }
        );

        TOOLTIP_MISSING_ANVIL = TextUtil.translateAndEdit(
                "tooltip."+Spellcraft.MOD_ID+".forge_hammer.missing_anvil",
                "Requires: An Anvil within 3 blocks.",
                component -> {
                    component.withStyle(ChatFormatting.RED);
                    component.withStyle(ChatFormatting.ITALIC);
                }
        );
    }
}
