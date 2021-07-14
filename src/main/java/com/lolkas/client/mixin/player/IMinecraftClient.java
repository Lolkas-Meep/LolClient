package com.lolkas.client.mixin.player;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface IMinecraftClient {
    @Invoker("doAttack")
    void leftClick();

    @Invoker("doItemUse")
    void rightClick();

    @Accessor("currentFps")
    int getFps();
}
