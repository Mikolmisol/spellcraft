package mikolmisol.spellcraft.spells.modifiers;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.EffectNode;
import mikolmisol.spellcraft.spells.effects.SpellcraftEffects;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.shapes.ShapeNode;
import mikolmisol.spellcraft.spells.shapes.SpellcraftShapes;
import mikolmisol.spellcraft.util.HashBiMap;
import net.minecraft.world.level.ClipContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class SpellcraftModifierHandlers {
    private final HashBiMap<Shape, Modifier, ModifierHandler<ShapeNode>> SHAPE_MODIFIER_HANDLERS
            = new HashBiMap<>();
    private final HashBiMap<Effect, Modifier, ModifierHandler<EffectNode>> EFFECT_MODIFIER_HANDLERS
            = new HashBiMap<>();

    static {
        setShapeHandler(
                SpellcraftShapes.PROJECTILE,
                SpellcraftModifiers.DISTANT,
                (shape, attributes) -> {
                    final var range = attributes.get(SpellcraftAttributeTypes.RANGE);

                    range.setRange(range.getRange() * 1.5);
                    shape.setCost(shape.getCost() * 1.5);
                }
        );

        setShapeHandler(
                SpellcraftShapes.PROJECTILE,
                SpellcraftModifiers.MASSLESS,
                (shape, attributes) -> {
                    final var gravity = attributes.get(SpellcraftAttributeTypes.GRAVITY);

                    gravity.setGravity(gravity.getGravity() * 0.75);
                    shape.setCost(shape.getCost() * 1.25);
                }
        );

        setShapeHandler(
                SpellcraftShapes.PROJECTILE,
                SpellcraftModifiers.SEROUS,
                (shape, attributes) -> {
                    final var fluidClipContext = attributes.get(SpellcraftAttributeTypes.FLUID_CLIP_CONTEXT);

                    fluidClipContext.setFluidClippingBehavior(ClipContext.Fluid.SOURCE_ONLY);
                }
        );

        setShapeHandler(
                SpellcraftShapes.PROJECTILE,
                SpellcraftModifiers.ETHEREAL,
                (shape, attributes) -> {
                    final var blockClipContext = attributes.get(SpellcraftAttributeTypes.BLOCK_CLIP_CONTEXT);

                    blockClipContext.setBlockClippingBehavior(ClipContext.Block.COLLIDER);
                }
        );


        setEffectHandler(
                SpellcraftEffects.FIRE,
                SpellcraftModifiers.POWERFUL,
                (effect, attributes) -> {
                    final var damage = attributes.get(SpellcraftAttributeTypes.FIRE_DAMAGE);

                    damage.setDamage(damage.getDamage() * 1.5f);
                    effect.setCost(effect.getCost() * 2);
                }
        );

        setEffectHandler(
                SpellcraftEffects.BREAK,
                SpellcraftModifiers.DIFFUSIVE,
                (effect, attributes) -> {
                    final var veinmine = attributes.get(SpellcraftAttributeTypes.VEINMINE);

                    veinmine.setVeinmine(true);
                    effect.setCost(effect.getCost() * 1.5);
                }
        );

        setEffectHandler(
                SpellcraftEffects.BREAK,
                SpellcraftModifiers.POWERFUL,
                (effect, attributes) -> {
                    final var miningLevel = attributes.get(SpellcraftAttributeTypes.MINING_LEVEL);

                    miningLevel.setMiningLevel(miningLevel.getMiningLevel() + 1);
                    effect.setCost(effect.getCost() * 2);
                }
        );

        setEffectHandler(
                SpellcraftEffects.BREAK,
                SpellcraftModifiers.SOFT,
                (effect, attributes) -> {
                    final var silkTouch = attributes.get(SpellcraftAttributeTypes.SILK_TOUCH);

                    silkTouch.setSilkTouch(true);
                    effect.setCost(effect.getCost() * 2);
                }
        );

        setEffectHandler(
                SpellcraftEffects.HARVEST,
                SpellcraftModifiers.POWERFUL,
                (effect, attributes) -> {
                    final var miningLevel = attributes.get(SpellcraftAttributeTypes.MINING_LEVEL);

                    miningLevel.setMiningLevel(miningLevel.getMiningLevel() + 1);
                    effect.setCost(effect.getCost() * 2);
                }
        );

        setEffectHandler(
                SpellcraftEffects.HARVEST,
                SpellcraftModifiers.SOFT,
                (effect, attributes) -> {
                    final var silkTouch = attributes.get(SpellcraftAttributeTypes.SILK_TOUCH);

                    silkTouch.setSilkTouch(true);
                    effect.setCost(effect.getCost() * 2);
                }
        );
    }

    public void setShapeHandler(@NotNull Shape shape, @NotNull Modifier modifier, @NotNull ModifierHandler<ShapeNode> handler) {
        SHAPE_MODIFIER_HANDLERS.put(shape, modifier, handler);
    }

    public @Nullable ModifierHandler<ShapeNode> getShapeHandler(@NotNull Shape shape, @NotNull Modifier modifier) {
        return SHAPE_MODIFIER_HANDLERS.get(shape, modifier);
    }

    public void setEffectHandler(@NotNull Effect effect, @NotNull Modifier modifier, @NotNull ModifierHandler<EffectNode> handler) {
        EFFECT_MODIFIER_HANDLERS.put(effect, modifier, handler);
    }

    public @Nullable ModifierHandler<EffectNode> getEffectHandler(@NotNull Effect effect, @NotNull Modifier modifier) {
        return EFFECT_MODIFIER_HANDLERS.get(effect, modifier);
    }

    public void initialise() {
    }
}
