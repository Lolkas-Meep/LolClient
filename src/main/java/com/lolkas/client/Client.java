package com.lolkas.client;

import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.gui.MenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Client implements ClientModInitializer {
    public static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
           "MainGui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "Gui"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.isPressed()) {
                MinecraftClient.getInstance().openScreen(new MenuScreen(new MenuGui()));
            }
        });
    }
    private static KeyBinding keyBinding;
}
