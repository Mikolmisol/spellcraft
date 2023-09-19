package mikolmisol.spellcraft.render_data_types;

import org.joml.Vector3f;

public record PrismaticBipyramidalCrystal(Vector3f upperApex, Vector3f middlePlaneA, Vector3f middlePlaneB,
                                          Vector3f middlePlaneC,
                                          Vector3f middlePlaneD, Vector3f lowerPlaneA, Vector3f lowerPlaneB,
                                          Vector3f lowerPlaneC,
                                          Vector3f lowerPlaneD) {
}
