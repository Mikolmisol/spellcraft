package mikolmisol.spellcraft.spells.effects;

import lombok.experimental.UtilityClass;
import mikolmisol.spellcraft.registries.SpellcraftRegistries;
import mikolmisol.spellcraft.spells.effects.impl.*;
import net.minecraft.core.Registry;

@UtilityClass
public class SpellcraftEffects {
    public final Effect FIRE = Registry.register(
            SpellcraftRegistries.EFFECT,
            Fire.IDENTIFIER,
            new Fire());

    public final Effect FROST = Registry.register(
            SpellcraftRegistries.EFFECT,
            Frost.IDENTIFIER,
            new Frost());

    public final Effect LIGHTNING = Registry.register(
            SpellcraftRegistries.EFFECT,
            Lightning.IDENTIFIER,
            new Lightning());

    public final Effect ARCANE = Registry.register(
            SpellcraftRegistries.EFFECT,
            Arcane.IDENTIFIER,
            new Arcane());

    public static final Effect ENTROPY = Registry.register(
            SpellcraftRegistries.EFFECT,
            Entropy.IDENTIFIER,
            new Entropy());

    public static final Effect DRAIN_LIFE = Registry.register(
            SpellcraftRegistries.EFFECT,
            DrainLife.IDENTIFIER,
            new DrainLife());

    public static final Effect BLOOD_CONTROL = Registry.register(
            SpellcraftRegistries.EFFECT,
            BloodControl.IDENTIFIER,
            new BloodControl());

    public static final Effect INQUISITION = Registry.register(
            SpellcraftRegistries.EFFECT,
            Judgement.IDENTIFIER,
            new Judgement());

    public static final Effect HEAL = Registry.register(
            SpellcraftRegistries.EFFECT,
            Heal.IDENTIFIER,
            new Heal());

    public static final Effect ARCANE_BARRIER = Registry.register(
            SpellcraftRegistries.EFFECT,
            ArcaneBarrier.IDENTIFIER,
            new ArcaneBarrier());

    public static final Effect BREAK = Registry.register(
            SpellcraftRegistries.EFFECT,
            Break.IDENTIFIER,
            new Break());

    public static final Effect HARVEST = Registry.register(
            SpellcraftRegistries.EFFECT,
            Harvest.IDENTIFIER,
            new Harvest());

    public static final Effect TRANSMUTE = Registry.register(
            SpellcraftRegistries.EFFECT,
            Transmute.IDENTIFIER,
            new Transmute());

    public void initialise() {
    }
}
