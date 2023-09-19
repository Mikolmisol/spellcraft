package mikolmisol.spellcraft.util;

public final class SinusoidalCounter {
    private final float lowerBound;

    private final float upperBound;

    private final float step;

    private float current;

    private boolean receding = false;

    public SinusoidalCounter(float lowerBound, float upperBound, float step) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
    }

    public float tick() {
        if (!receding) {
            current += step;

            if (current >= upperBound) {
                receding = true;
            }

            return current;
        }

        current -= step;

        if (current <= lowerBound) {
            receding = false;
        }

        return current;
    }
}
