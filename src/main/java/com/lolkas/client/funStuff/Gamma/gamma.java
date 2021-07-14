package com.lolkas.client.funStuff.Gamma;

import com.lolkas.client.gui.MenuGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class gamma implements ClientModInitializer {
    public static double gamma;

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(this::onStart);
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }

    private void tick(MinecraftClient client) {
        gamma = MenuGui.GammaSlider.getValue();
        client.options.gamma = gamma;
        MenuGui.GammaSlider.setLabel(new LiteralText(Double.toString(gamma)));
    }

    private void onStart(MinecraftClient client) {
        gamma = client.options.gamma;
        MenuGui.GammaSlider.setValue((int) gamma);
    }
}
