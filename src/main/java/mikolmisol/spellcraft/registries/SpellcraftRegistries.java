package mikolmisol.spellcraft.registries;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.parts.SpellcraftParts;
import mikolmisol.spellcraft.parts.brushes.Brush;
import mikolmisol.spellcraft.parts.foci.Focus;
import mikolmisol.spellcraft.parts.knob.Knob;
import mikolmisol.spellcraft.parts.rods.Rod;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientType;
import mikolmisol.spellcraft.recipes.ingredients.SpellcraftSpellRecipeIngredientTypes;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import mikolmisol.spellcraft.recipes.results.SpellcraftSpellRecipeResultTypes;
import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.attributes.SpellcraftAttributeTypes;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.effects.SpellcraftEffects;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.modifiers.SpellcraftModifiers;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.shapes.SpellcraftShapes;
import mikolmisol.spellcraft.spells.targets.SpellcraftTargetTypes;
import mikolmisol.spellcraft.spells.targets.TargetType;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.registerSimple;

@UtilityClass
public class SpellcraftRegistries {
    public final Registry<Shape> SHAPE;

    public final Registry<Effect> EFFECT;

    public final Registry<Modifier> MODIFIER;

    public final Registry<AttributeType<? extends Attribute>> ATTRIBUTE_TYPE;

    public final Registry<TargetType<?>> TARGET_TYPE;

    public final Registry<Brush> BRUSH;

    public final Registry<Focus> FOCUS;

    public final Registry<Knob> KNOB;

    public final Registry<Rod> ROD;

    public final Registry<SpellRecipeIngredientType<?>> SPELL_RECIPE_INGREDIENT_TYPE;

    public final Registry<SpellRecipeResultType<?>> SPELL_RECIPE_RESULT_TYPE;

    static {
        SHAPE = registerSimple(SpellcraftRegistryResourceKeys.SHAPE, registry -> SpellcraftShapes.TOUCH);

        MODIFIER = registerSimple(SpellcraftRegistryResourceKeys.MODIFIER, registry -> SpellcraftModifiers.POWERFUL);

        EFFECT = registerSimple(SpellcraftRegistryResourceKeys.EFFECT, registry -> SpellcraftEffects.FIRE);

        ATTRIBUTE_TYPE = registerSimple(SpellcraftRegistryResourceKeys.ATTRIBUTE_TYPE, registry -> SpellcraftAttributeTypes.RANGE);

        TARGET_TYPE = registerSimple(SpellcraftRegistryResourceKeys.TARGET_TYPE, registry -> SpellcraftTargetTypes.ITEM);

        BRUSH = registerSimple(SpellcraftRegistryResourceKeys.BRUSH, registry -> SpellcraftParts.Brushes.HAY);

        FOCUS = registerSimple(SpellcraftRegistryResourceKeys.FOCUS, registry -> SpellcraftParts.Foci.DIAMOND);

        KNOB = registerSimple(SpellcraftRegistryResourceKeys.KNOB, registry -> SpellcraftParts.Knobs.IRON);

        ROD = registerSimple(SpellcraftRegistryResourceKeys.ROD, registry -> SpellcraftParts.Rods.WOOD);

        SPELL_RECIPE_INGREDIENT_TYPE = registerSimple(SpellcraftRegistryResourceKeys.SPELL_RECIPE_INGREDIENT_TYPE, registry -> SpellcraftSpellRecipeIngredientTypes.ITEM);

        SPELL_RECIPE_RESULT_TYPE = registerSimple(SpellcraftRegistryResourceKeys.SPELL_RECIPE_RESULT_TYPE, registry -> SpellcraftSpellRecipeResultTypes.ITEM);
    }

    public void initialise() {
    }
}
