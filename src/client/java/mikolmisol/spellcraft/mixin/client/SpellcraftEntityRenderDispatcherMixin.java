package mikolmisol.spellcraft.mixin.client;

import com.google.common.collect.Maps;
import mikolmisol.spellcraft.entity_renderers.PolymorphRenderer;
import mikolmisol.spellcraft.mob_effects.SpellcraftMobEffects;
import mikolmisol.spellcraft.util.JavaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public abstract class SpellcraftEntityRenderDispatcherMixin {
    @Unique
    private final Map<Class<?>, PolymorphRenderer<? extends LivingEntity>> spellcraft_polymorphRenderersCache = Maps.newHashMap();
    @Shadow
    @Final
    private ItemRenderer itemRenderer;
    @Shadow
    @Final
    private BlockRenderDispatcher blockRenderDispatcher;
    @Shadow
    @Final
    private ItemInHandRenderer itemInHandRenderer;
    @Shadow
    @Final
    private EntityModelSet entityModels;
    @Shadow
    @Final
    private Font font;

    @Inject(
            at = @At("HEAD"),
            target = @Desc(
                    value = "getRenderer",
                    args = Entity.class,
                    ret = EntityRenderer.class
            ),
            cancellable = true
    )
    public <T extends Entity> EntityRenderer<? super T> spellcraft_onGetRenderer(T entity, CallbackInfoReturnable<EntityRenderer<? extends T>> callback) {
        if (entity instanceof LivingEntity living && living.hasEffect(SpellcraftMobEffects.POLYMORPH)) {
            var renderer = spellcraft_polymorphRenderersCache.get(entity.getClass());

            if (renderer == null) {
                final var context = new EntityRendererProvider.Context(JavaUtil.cast(this), itemRenderer, blockRenderDispatcher, itemInHandRenderer, Minecraft.getInstance().getResourceManager(), entityModels, font);
                renderer = new PolymorphRenderer<>(context);
                spellcraft_polymorphRenderersCache.put(entity.getClass(), renderer);
            }

            callback.setReturnValue(JavaUtil.cast(renderer));
            callback.cancel();
        }

        return null;
    }
}
