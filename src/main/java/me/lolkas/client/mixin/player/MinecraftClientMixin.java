package me.lolkas.client.mixin.player;

import me.lolkas.client.helper.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "stop", at = @At("HEAD"))
    public void onStop(CallbackInfo ci){
        ConfigManager.saveConfigs();
    }
}
