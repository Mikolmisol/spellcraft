package mikolmisol.spellcraft.render_data_types;

import org.joml.Vector3f;

import java.util.Random;

public record BipyramidalCrystal(Vector3f upperApex, Vector3f middlePlaneA, Vector3f middlePlaneB, Vector3f middlePlaneC,
                                 Vector3f middlePlaneD, Vector3f lowerApex) {

    private static final BipyramidalCrystal[][] CRYSTALS;

    static {
        final var crystalsForRichness0 = getRandomCrystalsForRichness(0);
        final var crystalsForRichness1 = getRandomCrystalsForRichness(1);
        final var crystalsForRichness2 = getRandomCrystalsForRichness(2);

        CRYSTALS = new BipyramidalCrystal[][]{crystalsForRichness0, crystalsForRichness1, crystalsForRichness2};
    }

    public static BipyramidalCrystal[] getRandomCrystalsForRichness(int richness) {
        final var random = new Random();

        return switch (richness) {
            case 0 -> new BipyramidalCrystal[]{
                    withRandomOffset(random,
                            new Vector3f(0.5f, 0.8f, 0.5f),
                            new Vector3f(0.4f, 1, 0.4f),
                            new Vector3f(0.4f, 1, 0.6f),
                            new Vector3f(0.6f, 1, 0.6f),
                            new Vector3f(0.6f, 1, 0.4f),
                            new Vector3f(0.5f, 1.2f, 0.5f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.5f, 0.2f, 0.5f),
                            new Vector3f(0.4f, 0, 0.4f),
                            new Vector3f(0.4f, 0, 0.6f),
                            new Vector3f(0.6f, 0, 0.6f),
                            new Vector3f(0.6f, 0, 0.4f),
                            new Vector3f(0.5f, -0.2f, 0.5f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(1.2f, 0.5f, 0.5f),
                            new Vector3f(1, 0.4f, 0.4f),
                            new Vector3f(1, 0.4f, 0.6f),
                            new Vector3f(1, 0.6f, 0.6f),
                            new Vector3f(1, 0.6f, 0.4f),
                            new Vector3f(0.8f, 0.5f, 0.5f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.2f, 0.5f, 0.5f),
                            new Vector3f(0, 0.4f, 0.4f),
                            new Vector3f(0, 0.4f, 0.6f),
                            new Vector3f(0, 0.6f, 0.6f),
                            new Vector3f(0, 0.6f, 0.4f),
                            new Vector3f(-0.2f, 0.5f, 0.5f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.5f, 0.5f, 1.2f),
                            new Vector3f(0.4f, 0.4f, 1),
                            new Vector3f(0.6f, 0.4f, 1),
                            new Vector3f(0.6f, 0.6f, 1),
                            new Vector3f(0.4f, 0.6f, 1),
                            new Vector3f(0.5f, 0.5f, 0.8f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.5f, 0.5f, 0.2f),
                            new Vector3f(0.4f, 0.4f, 0),
                            new Vector3f(0.6f, 0.4f, 0),
                            new Vector3f(0.6f, 0.6f, 0),
                            new Vector3f(0.4f, 0.6f, 0),
                            new Vector3f(0.5f, 0.5f, -0.2f)
                    )
            };
            case 1 -> new BipyramidalCrystal[]{
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.8f, 0.3f),
                            new Vector3f(0.5f, 1, 0.2f),
                            new Vector3f(0.5f, 1, 0.4f),
                            new Vector3f(0.7f, 1, 0.4f),
                            new Vector3f(0.7f, 1, 0.2f),
                            new Vector3f(0.6f, 1.2f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.8f, 0.6f),
                            new Vector3f(0.2f, 1, 0.5f),
                            new Vector3f(0.2f, 1, 0.7f),
                            new Vector3f(0.4f, 1, 0.7f),
                            new Vector3f(0.4f, 1, 0.5f),
                            new Vector3f(0.3f, 1.2f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(1.2f, 0.6f, 0.3f),
                            new Vector3f(1, 0.5f, 0.2f),
                            new Vector3f(1, 0.5f, 0.4f),
                            new Vector3f(1, 0.7f, 0.4f),
                            new Vector3f(1, 0.7f, 0.2f),
                            new Vector3f(0.8f, 0.6f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(1.2f, 0.3f, 0.6f),
                            new Vector3f(1, 0.2f, 0.5f),
                            new Vector3f(1, 0.2f, 0.7f),
                            new Vector3f(1, 0.4f, 0.7f),
                            new Vector3f(1, 0.4f, 0.5f),
                            new Vector3f(0.8f, 0.3f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.2f, 0.6f, 0.3f),
                            new Vector3f(0, 0.5f, 0.2f),
                            new Vector3f(0, 0.5f, 0.4f),
                            new Vector3f(0, 0.7f, 0.4f),
                            new Vector3f(0, 0.7f, 0.2f),
                            new Vector3f(-0.2f, 0.6f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.2f, 0.3f, 0.6f),
                            new Vector3f(0, 0.2f, 0.5f),
                            new Vector3f(0, 0.2f, 0.7f),
                            new Vector3f(0, 0.4f, 0.7f),
                            new Vector3f(0, 0.4f, 0.5f),
                            new Vector3f(-0.2f, 0.3f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.6f, 1.2f),
                            new Vector3f(0.2f, 0.5f, 1),
                            new Vector3f(0.4f, 0.5f, 1),
                            new Vector3f(0.4f, 0.7f, 1),
                            new Vector3f(0.2f, 0.7f, 1),
                            new Vector3f(0.3f, 0.6f, 0.8f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.3f, 1.2f),
                            new Vector3f(0.5f, 0.2f, 1),
                            new Vector3f(0.7f, 0.2f, 1),
                            new Vector3f(0.7f, 0.4f, 1),
                            new Vector3f(0.5f, 0.4f, 1),
                            new Vector3f(0.6f, 0.3f, 0.8f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.6f, 0.2f),
                            new Vector3f(0.2f, 0.5f, 0),
                            new Vector3f(0.4f, 0.5f, 0),
                            new Vector3f(0.4f, 0.7f, 0),
                            new Vector3f(0.2f, 0.7f, 0),
                            new Vector3f(0.3f, 0.6f, -0.2f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.3f, 0.2f),
                            new Vector3f(0.5f, 0.2f, 0),
                            new Vector3f(0.7f, 0.2f, 0),
                            new Vector3f(0.7f, 0.4f, 0),
                            new Vector3f(0.5f, 0.4f, 0),
                            new Vector3f(0.6f, 0.3f, -0.2f)
                    )
            };
            default -> new BipyramidalCrystal[]{
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.8f, 0.3f),
                            new Vector3f(0.5f, 1, 0.2f),
                            new Vector3f(0.5f, 1, 0.4f),
                            new Vector3f(0.7f, 1, 0.4f),
                            new Vector3f(0.7f, 1, 0.2f),
                            new Vector3f(0.6f, 1.2f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.8f, 0.6f),
                            new Vector3f(0.2f, 1, 0.5f),
                            new Vector3f(0.2f, 1, 0.7f),
                            new Vector3f(0.4f, 1, 0.7f),
                            new Vector3f(0.4f, 1, 0.5f),
                            new Vector3f(0.3f, 1.2f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.7f, 0.8f, 0.7f),
                            new Vector3f(0.6f, 1, 0.6f),
                            new Vector3f(0.6f, 1, 0.8f),
                            new Vector3f(0.8f, 1, 0.8f),
                            new Vector3f(0.8f, 1, 0.6f),
                            new Vector3f(0.7f, 1.2f, 0.7f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.2f, 0.3f),
                            new Vector3f(0.5f, 0, 0.2f),
                            new Vector3f(0.5f, 0, 0.4f),
                            new Vector3f(0.7f, 0, 0.4f),
                            new Vector3f(0.7f, 0, 0.2f),
                            new Vector3f(0.6f, -0.2f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.2f, 0.6f),
                            new Vector3f(0.2f, 0, 0.5f),
                            new Vector3f(0.2f, 0, 0.7f),
                            new Vector3f(0.4f, 0, 0.7f),
                            new Vector3f(0.4f, 0, 0.5f),
                            new Vector3f(0.3f, -0.2f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.7f, 0.2f, 0.7f),
                            new Vector3f(0.6f, 0, 0.6f),
                            new Vector3f(0.6f, 0, 0.8f),
                            new Vector3f(0.8f, 0, 0.8f),
                            new Vector3f(0.8f, 0, 0.6f),
                            new Vector3f(0.7f, -0.2f, 0.7f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(1.2f, 0.6f, 0.3f),
                            new Vector3f(1, 0.5f, 0.2f),
                            new Vector3f(1, 0.5f, 0.4f),
                            new Vector3f(1, 0.7f, 0.4f),
                            new Vector3f(1, 0.7f, 0.2f),
                            new Vector3f(0.8f, 0.6f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(1.2f, 0.3f, 0.6f),
                            new Vector3f(1, 0.2f, 0.5f),
                            new Vector3f(1, 0.2f, 0.7f),
                            new Vector3f(1, 0.4f, 0.7f),
                            new Vector3f(1, 0.4f, 0.5f),
                            new Vector3f(0.8f, 0.3f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(1.2f, 0.7f, 0.7f),
                            new Vector3f(1, 0.6f, 0.6f),
                            new Vector3f(1, 0.6f, 0.8f),
                            new Vector3f(1, 0.8f, 0.8f),
                            new Vector3f(1, 0.8f, 0.6f),
                            new Vector3f(0.8f, 0.7f, 0.7f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.2f, 0.6f, 0.3f),
                            new Vector3f(0, 0.5f, 0.2f),
                            new Vector3f(0, 0.5f, 0.4f),
                            new Vector3f(0, 0.7f, 0.4f),
                            new Vector3f(0, 0.7f, 0.2f),
                            new Vector3f(-0.2f, 0.6f, 0.3f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.2f, 0.3f, 0.6f),
                            new Vector3f(0, 0.2f, 0.5f),
                            new Vector3f(0, 0.2f, 0.7f),
                            new Vector3f(0, 0.4f, 0.7f),
                            new Vector3f(0, 0.4f, 0.5f),
                            new Vector3f(-0.2f, 0.3f, 0.6f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.2f, 0.7f, 0.7f),
                            new Vector3f(0, 0.6f, 0.6f),
                            new Vector3f(0, 0.6f, 0.8f),
                            new Vector3f(0, 0.8f, 0.8f),
                            new Vector3f(0, 0.8f, 0.6f),
                            new Vector3f(-0.2f, 0.7f, 0.7f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.6f, 1.2f),
                            new Vector3f(0.2f, 0.5f, 1),
                            new Vector3f(0.4f, 0.5f, 1),
                            new Vector3f(0.4f, 0.7f, 1),
                            new Vector3f(0.2f, 0.7f, 1),
                            new Vector3f(0.3f, 0.6f, 0.8f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.3f, 1.2f),
                            new Vector3f(0.5f, 0.2f, 1),
                            new Vector3f(0.7f, 0.2f, 1),
                            new Vector3f(0.7f, 0.4f, 1),
                            new Vector3f(0.5f, 0.4f, 1),
                            new Vector3f(0.6f, 0.3f, 0.8f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.7f, 0.7f, 1.2f),
                            new Vector3f(0.6f, 0.6f, 1),
                            new Vector3f(0.8f, 0.6f, 1),
                            new Vector3f(0.8f, 0.8f, 1),
                            new Vector3f(0.6f, 0.8f, 1),
                            new Vector3f(0.7f, 0.7f, 0.8f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.3f, 0.6f, 0.2f),
                            new Vector3f(0.2f, 0.5f, 0),
                            new Vector3f(0.4f, 0.5f, 0),
                            new Vector3f(0.4f, 0.7f, 0),
                            new Vector3f(0.2f, 0.7f, 0),
                            new Vector3f(0.3f, 0.6f, -0.2f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.6f, 0.3f, 0.2f),
                            new Vector3f(0.5f, 0.2f, 0),
                            new Vector3f(0.7f, 0.2f, 0),
                            new Vector3f(0.7f, 0.4f, 0),
                            new Vector3f(0.5f, 0.4f, 0),
                            new Vector3f(0.6f, 0.3f, -0.2f)
                    ),
                    withRandomOffset(random,
                            new Vector3f(0.7f, 0.7f, 0.2f),
                            new Vector3f(0.6f, 0.6f, 0),
                            new Vector3f(0.8f, 0.6f, 0),
                            new Vector3f(0.8f, 0.8f, 0),
                            new Vector3f(0.6f, 0.8f, 0),
                            new Vector3f(0.7f, 0.7f, -0.2f)
                    )
            };
        };
    }

    public static BipyramidalCrystal[] getCrystalsForRichness(int richness) {
        return CRYSTALS[richness];
    }

    private static BipyramidalCrystal withRandomOffset(Random random, Vector3f upperApex, Vector3f middlePlane1, Vector3f middlePlane2, Vector3f middlePlane3, Vector3f middlePlane4, Vector3f lowerApex) {
        final var yVariance = random.nextFloat(-0.1f, 0.1f);
        final var xVariance = random.nextFloat(-0.1f, 0.1f);
        final var zVariance = random.nextFloat(-0.1f, 0.1f);

        return new BipyramidalCrystal(
                new Vector3f(upperApex.x() + xVariance, upperApex.y() + yVariance, upperApex.z() + zVariance),
                new Vector3f(middlePlane1.x() + xVariance, middlePlane1.y() + yVariance, middlePlane1.z() + zVariance),
                new Vector3f(middlePlane2.x() + xVariance, middlePlane2.y() + yVariance, middlePlane2.z() + zVariance),
                new Vector3f(middlePlane3.x() + xVariance, middlePlane3.y() + yVariance, middlePlane3.z() + zVariance),
                new Vector3f(middlePlane4.x() + xVariance, middlePlane4.y() + yVariance, middlePlane4.z() + zVariance),
                new Vector3f(lowerApex.x() + xVariance, lowerApex.y() + yVariance, lowerApex.z() + zVariance)
        );
    }
}
