package me.lolkas.client.mixin.world;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void shouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir){
        Module module = ModuleRegistry.getByName("NoRenderWithers");
        if(module.isEnabled() && (entity instanceof WitherEntity || entity instanceof WitherSkullEntity)){
            cir.cancel();
        }
    }
}
