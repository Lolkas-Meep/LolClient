package me.lolkas.client.funStuff.modules.smallMods.hud;

import me.lolkas.client.funStuff.modules.smallMods.save.Save;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class tick implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(this::start);
    }

    private void start(MinecraftClient client) {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        coords.start();
    }

    private void tick(MinecraftClient client) {
        coords.renderText();
        try {
            Save.tick();
        }catch (Exception e){

        }
    }
}
