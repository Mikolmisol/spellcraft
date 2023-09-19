package mikolmisol.spellcraft.metals;

import lombok.Getter;

public enum Variant {
    NUGGET("%s_nugget", new String[]{"%s_nuggets"}),
    INGOT("%s_ingot", new String[]{"%s_ingots"}),
    BLOCK("%s_block", new String[]{"%s_blocks"}),

    TINY_PILE_OF_GRIT("tiny_pile_of_%s_grit", new String[]{"%s_tiny_dusts", "%s_small_dusts"}),
    GRIT("%s_grit", new String[]{"%s_dusts"}),
    GRIT_BLOCK("%s_grit_block", new String[]{"%s_dust_blocks"}),

    PLATE("%s_plate", new String[]{"%s_plates"}),
    PLATING("%s_plating", new String[]{"%s_plating"});

    @Getter
    private final String pattern;

    @Getter
    private final String[] tagPatterns;

    Variant(String pattern, String[] tagPatterns) {
        this.pattern = pattern;
        this.tagPatterns = tagPatterns;
    }
}
