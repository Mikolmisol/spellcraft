package mikolmisol.spellcraft.registries;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.parts.brushes.Brush;
import mikolmisol.spellcraft.parts.foci.Focus;
import mikolmisol.spellcraft.parts.knob.Knob;
import mikolmisol.spellcraft.parts.rods.Rod;
import mikolmisol.spellcraft.recipes.ingredients.SpellRecipeIngredientType;
import mikolmisol.spellcraft.recipes.results.SpellRecipeResultType;
import mikolmisol.spellcraft.spells.attributes.Attribute;
import mikolmisol.spellcraft.spells.attributes.AttributeType;
import mikolmisol.spellcraft.spells.effects.Effect;
import mikolmisol.spellcraft.spells.modifiers.Modifier;
import mikolmisol.spellcraft.spells.shapes.Shape;
import mikolmisol.spellcraft.spells.targets.TargetType;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static mikolmisol.spellcraft.Spellcraft.MOD_ID;

@UtilityClass
public class SpellcraftRegistryResourceKeys {

    public final ResourceKey<Registry<Shape>> SHAPE;

    public final ResourceKey<Registry<Effect>> EFFECT;

    public final ResourceKey<Registry<Modifier>> MODIFIER;

    public final ResourceKey<Registry<AttributeType<? extends Attribute>>> ATTRIBUTE_TYPE;

    public final ResourceKey<Registry<TargetType<?>>> TARGET_TYPE;

    public final ResourceKey<Registry<Brush>> BRUSH;

    public final ResourceKey<Registry<Focus>> FOCUS;

    public final ResourceKey<Registry<Knob>> KNOB;

    public final ResourceKey<Registry<Rod>> ROD;

    public final ResourceKey<Registry<SpellRecipeIngredientType<?>>> SPELL_RECIPE_INGREDIENT_TYPE;

    public final ResourceKey<Registry<SpellRecipeResultType<?>>> SPELL_RECIPE_RESULT_TYPE;

    static {
        SHAPE = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "shape")));

        EFFECT = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "effect")));

        MODIFIER = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "modifier")));

        ATTRIBUTE_TYPE = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "attribute_type")));

        TARGET_TYPE = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "target_type")));

        BRUSH = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "brush")));

        FOCUS = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "focus")));

        KNOB = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "knob")));

        ROD = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "rod")));

        SPELL_RECIPE_INGREDIENT_TYPE = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "spell_recipe_ingredient_type")));

        SPELL_RECIPE_RESULT_TYPE = JavaUtil.cast(ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "spell_recipe_result_type")));
    }

    public void initialise() {
    }
}
