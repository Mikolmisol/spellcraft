package mikolmisol.spellcraft.damage;

import lombok.val;
import mikolmisol.spellcraft.spells.Spell;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpellDamageSource extends DamageSource {
    private final @NotNull Spell spell;

    public SpellDamageSource(Holder<DamageType> holder, @Nullable Entity target, @Nullable Entity caster, @NotNull Spell spell) {
        super(holder, target, caster);
        this.spell = spell;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        val message = "death.spellcraft.spell." + type().msgId();

        val target = getDirectEntity();

        if (causingEntity != null) {
            return Component.translatable(message, livingEntity.getDisplayName(), causingEntity.getDisplayName());
        }

        if (target != null) {
            return Component.translatable(message, livingEntity.getDisplayName(), target.getDisplayName());
        }

        val player = livingEntity.getKillCredit();
        String string2 = message + ".player";

        if (player != null) {
            return Component.translatable(string2, livingEntity.getDisplayName(), player.getDisplayName());
        }

        return Component.translatable(message, livingEntity.getDisplayName());
    }
}
