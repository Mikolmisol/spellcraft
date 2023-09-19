package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TimeUtil {

    public double ticksToSeconds(int ticks) {
        return ticks / 20.0;
    }

    public int secondsToTicks(double seconds) {
        return (int) (seconds * 20);
    }
}
