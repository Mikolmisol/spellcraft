package mikolmisol.spellcraft.util;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

@UtilityClass
public final class JavaUtil {
    private final Consumer<?> EMPTY_CONSUMER = x -> {
    };

    private final Runnable EMPTY_RUNNABLE = () -> {
    };

    public <T> Consumer<T> getEmptyConsumer() {
        return (Consumer<T>) EMPTY_CONSUMER;
    }

    public Runnable getEmptyRunnable() {
        return EMPTY_RUNNABLE;
    }

    public <T> T cast(Object object) {
        return (T) object;
    }
}
