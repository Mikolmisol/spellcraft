package mikolmisol.spellcraft.spells.shapes.impl;

import com.google.common.collect.Lists;
import mikolmisol.spellcraft.Spellcraft;
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
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class Nova implements Shape {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Spellcraft.MOD_ID, "nova");

    private static final double BASE_COST = 15;

    private static final double BASE_RANGE = 5;

    private static final Component NAME = Component.translatable("spell.shape.spellcraft.nova");

    private static final ProvidedTargets PROVIDED_TARGETS = ProvidedTargets.of(SpellcraftTargetTypes.BLOCK, SpellcraftTargetTypes.ENTITY);

    @Override
    public void cast(Spell spell, Caster caster, Level level) {
        final var attributes = spell.getShape().getAttributes();

        final var range = attributes.get(SpellcraftAttributeTypes.RANGE).getRange();

        final var exactPosition = caster.getSpellCastingPosition();
        final var box = new AABB(exactPosition.x - range, exactPosition.y - range, exactPosition.z - range, exactPosition.x + range, exactPosition.y + range, exactPosition.z + range);

        final var entities =
                caster instanceof BlockEntity blockEntityCaster ?
                        GeomUtil.getAllEntitiesInLineOfSight(box, level, blockEntityCaster.getBlockPos()).stream().map(Target::ofEntity).toList()
                        : caster instanceof Entity entityCaster ?
                        GeomUtil.getAllEntitiesInLineOfSight(box, level, entityCaster).stream().map(Target::ofEntity).toList()
                        : GeomUtil.getAllEntitiesInLineOfSight(box, level, exactPosition).stream().map(Target::ofEntity).toList();

        final var blocks =
                caster instanceof BlockEntity blockEntityCaster ?
                        GeomUtil.getAllBlockPositionsInLineOfSight(box, level, blockEntityCaster.getBlockPos()).stream().map(position -> {
                            final var directionVector = exactPosition.vectorTo(Vec3.atCenterOf(position));
                            return Target.ofBlock(position, Direction.getNearest(directionVector.x, directionVector.y, directionVector.z));
                        }).toList()
                        : GeomUtil.getAllBlockPositionsInLineOfSight(box, level, exactPosition).stream().map(position -> {
                    final var directionVector = exactPosition.vectorTo(Vec3.atCenterOf(position));
                    return Target.ofBlock(position, Direction.getNearest(directionVector.x, directionVector.y, directionVector.z));
                }).toList();

        final var allTargets = Lists.<Target<?>>newArrayList(entities);

        allTargets.addAll(blocks);

        final var targets = Targets.of(allTargets);

        for (final var effect : spell.getShape().getEffects()) {
            effect.getEffect().cast(spell, caster, targets, level);
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
