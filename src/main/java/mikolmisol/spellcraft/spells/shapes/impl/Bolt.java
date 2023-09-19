package mikolmisol.spellcraft.spells.shapes.impl;

import mikolmisol.spellcraft.Spellcraft;
import mikolmisol.spellcraft.block_entities.coil.ArcaneCoil;
import mikolmisol.spellcraft.entities.SpellcraftEntityTypes;
import mikolmisol.spellcraft.entities.impl.SpellBolt;
import mikolmisol.spellcraft.spells.Caster;
import mikolmisol.spellcraft.spells.Spell;
import mikolmisol.spellcraft.spells.attributes.Attributes;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.attributes.impl.RangeAttribute;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.targets.ProvidedTargets;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.Target;
import mikolmisol.spellcraft.spells.targets.Targets;
import mikolmisol.spellcraft.util.GeomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public final class Bolt implements Shape {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "bolt");
    private static final double BASE_COST = 15;
    private static final double BASE_RANGE = 15;
    private static final Component NAME = Component.translatable("spell.shape.spellcraft.bolt");
    private static final ProvidedTargets PROVIDED_TARGETS = ProvidedTargets.of(SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    private static BlockHitResult clip(ClipContext context, BiFunction<ClipContext, BlockPos, @Nullable BlockHitResult> onHit) {
        final var origin = context.getFrom();
        final var end = context.getTo();

        if (origin.equals(end)) {
            final var to = context.getTo();
            final var missPosition = context.getFrom().subtract(to);
            return BlockHitResult.miss(context.getTo(), Direction.getNearest(missPosition.x, missPosition.y, missPosition.z), new BlockPos(GeomUtil.asVec3i(context.getTo())));
        }

        final var d = Mth.lerp(-1.0E-7, end.x, origin.x);
        final var e = Mth.lerp(-1.0E-7, end.y, origin.y);
        final var f = Mth.lerp(-1.0E-7, end.z, origin.z);
        final var g = Mth.lerp(-1.0E-7, origin.x, end.x);
        final var h = Mth.lerp(-1.0E-7, origin.y, end.y);
        final var i = Mth.lerp(-1.0E-7, origin.z, end.z);

        int j = Mth.floor(g);
        int k = Mth.floor(h);
        int l = Mth.floor(i);

        final var mutableBlockPos = new BlockPos.MutableBlockPos(j, k, l);
        final var firstHit = onHit.apply(context, mutableBlockPos);

        if (firstHit != null) {
            return firstHit;
        }

        final var m = d - g;
        final var n = e - h;
        final var o = f - i;

        int p = Mth.sign(m);
        int q = Mth.sign(n);
        int r = Mth.sign(o);

        final var s = p == 0 ? Double.MAX_VALUE : (double) p / m;
        final var t = q == 0 ? Double.MAX_VALUE : (double) q / n;
        final var u = r == 0 ? Double.MAX_VALUE : (double) r / o;

        var v = s * (p > 0 ? 1.0 - Mth.frac(g) : Mth.frac(g));
        var w = t * (q > 0 ? 1.0 - Mth.frac(h) : Mth.frac(h));
        var x = u * (r > 0 ? 1.0 - Mth.frac(i) : Mth.frac(i));

        var anotherHit = (BlockHitResult) null;
        do {
            if (!(v <= 1.0) && !(w <= 1.0) && !(x <= 1.0)) {
                final var missPosition = context.getFrom().subtract(context.getTo());
                return BlockHitResult.miss(context.getTo(), Direction.getNearest(missPosition.x, missPosition.y, missPosition.z), new BlockPos(GeomUtil.asVec3i(context.getTo())));
            }

            if (v < w) {
                if (v < x) {
                    j += p;
                    v += s;
                } else {
                    l += r;
                    x += u;
                }
            } else if (w < x) {
                k += q;
                w += t;
            } else {
                l += r;
                x += u;
            }

            anotherHit = onHit.apply(context, mutableBlockPos.set(j, k, l));
        } while (anotherHit == null);

        return anotherHit;
    }

    @Override
    public void cast(final Spell spell, final Caster caster, final Level level) {
        final var attributes = spell.getShape().getAttributes();

        final var rangeAttribute = attributes.get(SpellcraftAttributeTypes.RANGE);
        final var range = rangeAttribute.getRange();

        if (caster instanceof ServerPlayer player) {
            Spellcraft.sendRequestRaycastPacket(player, range, targets -> {
                final var bolt = new SpellBolt(SpellcraftEntityTypes.SPELL_BOLT, level);

                final var entityTargets = targets.getAllOfType(SpellcraftTargetTypes.ENTITY);
                final var blockTargets = targets.getAllOfType(SpellcraftTargetTypes.BLOCK);

                if (entityTargets.size() > 0) {
                    final var entityTarget = entityTargets.get(0);
                    final var entity = entityTarget.getValue();
                    bolt.strike(player.getEyePosition().subtract(0, 0.25, 0), entity.position().add(0, entity.getBbHeight() / 2, 0), spell.getDecimalColor());
                    bolt.moveTo(player.getEyePosition().subtract(0, 0.5, 0));
                    level.addFreshEntity(bolt);

                } else if (blockTargets.size() > 0) {
                    final var pos = blockTargets.get(0).getValue().position();
                    bolt.strike(player.getEyePosition().subtract(0, 0.25, 0), new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), spell.getDecimalColor());
                    bolt.moveTo(player.getEyePosition().subtract(0, 0.5, 0));
                    level.addFreshEntity(bolt);
                }

                for (final var effect : spell.getShape().getEffects()) {
                    effect.getEffect().cast(spell, caster, targets, level);
                }
            });
        } else if (caster instanceof ArcaneCoil coil) {
            final var position = coil.getArcaneCoilTipPosition();

            final var castingPosition = caster.getSpellCastingPosition();

            final var volume = new AABB(
                    castingPosition.x - range, castingPosition.y - 1.5, castingPosition.z - range,
                    castingPosition.x + range, castingPosition.y + range - 1.5, castingPosition.z + range
            );

            final var entity = level.getNearestEntity(LivingEntity.class, TargetingConditions.forNonCombat().range(range).ignoreInvisibilityTesting().ignoreLineOfSight().selector(potentialTarget -> {
                final var entityMiddlePosition = potentialTarget.getEyePosition();
                final var hit = clip(
                        new ClipContext(castingPosition, entityMiddlePosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, potentialTarget),
                        (context, hitPosition) -> {
                            if (hitPosition.equals(position)) {
                                return null;
                            }

                            final var hitBlock = level.getBlockState(hitPosition);
                            final var hitFluid = level.getFluidState(hitPosition);
                            final var blockShape = context.getBlockShape(hitBlock, level, hitPosition);
                            final var blockHit = level.clipWithInteractionOverride(castingPosition, entityMiddlePosition, hitPosition, blockShape, hitBlock);
                            final var fluidShape = context.getFluidShape(hitFluid, level, hitPosition);
                            final var fluidBlockHit = fluidShape.clip(castingPosition, entityMiddlePosition, hitPosition);
                            final var d = blockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(blockHit.getLocation());
                            final var e = fluidBlockHit == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(fluidBlockHit.getLocation());
                            return d <= e ? blockHit : fluidBlockHit;
                        }
                );
                if (hit.getType() == HitResult.Type.MISS) {
                    return true;
                }
                return hit.getBlockPos().equals(position);
            }), null, castingPosition.x, castingPosition.y, castingPosition.z, volume);

            if (entity == null) {
                return;
            }

            final var effects = spell.getShape().getEffects();
            for (final var effect : effects) {
                effect.getEffect().cast(spell, caster, Targets.of(Target.ofEntity(entity)), level);
            }
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
        attributes.create(SpellcraftAttributeTypes.RANGE, new RangeAttribute(BASE_RANGE));
    }
}
