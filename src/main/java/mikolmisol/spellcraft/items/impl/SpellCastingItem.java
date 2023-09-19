package mikolmisol.spellcraft.items.impl;

import mikolmisol.spellcraft.items.OnStartUseTick;
import mikolmisol.spellcraft.items.SpellContainingItem;
import mikolmisol.spellcraft.spells.Caster;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class SpellCastingItem extends Item implements SpellContainingItem, OnStartUseTick {
    public SpellCastingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onStartUseTick(@NotNull LivingEntity user, @NotNull InteractionHand hand, @NotNull ItemStack item) {
        final var spell = getSpell(item);

        if (spell == null) {
            return;
        }

        if (user instanceof Caster caster) {
            caster.spellcraft_startCasting(spell, user.level);
        }
    }

    @Override
    public void releaseUsing(ItemStack item, Level level, LivingEntity user, int remainingUseTicks) {
        final var spell = getSpell(item);

        if (spell == null) {
            return;
        }

        if (user instanceof Caster caster) {
            spell.cast(caster, user.level);
        }
    }

    @Override
    public int getUseDuration(ItemStack item) {
        final var spell = getSpell(item);

        if (spell == null) {
            return 0;
        }

        return (int) spell.getCost();
    }
}
