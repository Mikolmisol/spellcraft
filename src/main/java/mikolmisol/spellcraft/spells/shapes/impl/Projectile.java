package mikolmisol.spellcraft.spells.shapes.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.entities.impl.SpellProjectile;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Target;
import mikolmisol.spellcraft.spells.targets.Targets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public final class Projectile implements Shape {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "projectile");

    private static final double BASE_COST = 10;

    private static final Component NAME = Component.translatable("spell.shape.spellcraft.projectile");

    private static final ProvidedTargets PROVIDED_TARGETS = ProvidedTargets.of(SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    @Override
    public void cast(final Spell spell, final Caster caster, final Level level) {

        if (caster instanceof Entity entity) {
            final var projectile = SpellProjectile.create(level, caster, spell, hit -> {
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

                for (final var effect : spell.getShape().getEffects()) {
                    effect.getEffect().cast(spell, caster, Targets.of(targets), level);
                }
            });

            projectile.setPos(entity.getEyePosition());
            projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }
    }

    @Override
    public double getCost() {
        return BASE_COST;
    }

    @Override
    public @NotNull Component getName() {
        return NAME;
    }

    @Override
    public @NotNull ProvidedTargets getProvidedTargets() {
        return PROVIDED_TARGETS;
    }

    @Override
    public @NotNull ResourceLocation getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void defineBaseAttributes(@NotNull Attributes attributes) {

    }

}
