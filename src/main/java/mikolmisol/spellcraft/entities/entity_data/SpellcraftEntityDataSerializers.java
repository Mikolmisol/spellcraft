package mikolmisol.spellcraft.entities.entity_data;

import lombok.experimental.UtilityClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.phys.Vec3;

@UtilityClass
public class SpellcraftEntityDataSerializers {
    public final EntityDataSerializer<Double> DOUBLE = EntityDataSerializer.simple(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble);

    public final EntityDataSerializer<Vec3> VEC3 = EntityDataSerializer.simple(
            (buf, vec3) -> {
                buf.writeDouble(vec3.x);
                buf.writeDouble(vec3.y);
                buf.writeDouble(vec3.z);
            },
            buf -> new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble())
    );

    static {
        EntityDataSerializers.registerSerializer(DOUBLE);
        EntityDataSerializers.registerSerializer(VEC3);
    }
}
