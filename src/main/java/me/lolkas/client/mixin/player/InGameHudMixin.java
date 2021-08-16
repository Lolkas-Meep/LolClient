package me.lolkas.client.mixin.player;

import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.render.Hud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    Hud hud = (Hud) ModuleRegistry.getByClass(Hud.class);

    @Inject(method = "render", at = @At("RETURN"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci){
        hud.onHudRender();
    }
}
