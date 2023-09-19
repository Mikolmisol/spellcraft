package mikolmisol.spellcraft.recipes.ingredients;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public final class LivingEntityIngredient<T extends LivingEntity> {
    private final EntityType<T> type;
    private final float health;

    public LivingEntityIngredient(final @NotNull EntityType<T> type, final float health) {
        this.type = type;
        this.health = health;
    }

    public @NotNull EntityType<T> getType() {
        return type;
    }

    public float getHealth() {
        return health;
    }
}
