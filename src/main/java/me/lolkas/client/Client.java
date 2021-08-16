package me.lolkas.client;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.config.keybind.KeybindManager;
import me.lolkas.client.gui.ClickGui.ClickScreen;
import me.lolkas.client.helper.config.ConfigManager;
import me.lolkas.client.utills.FontUtills;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Client implements ClientModInitializer {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static FontUtills fontRenderer;
    public static Thread thread;

    @Override
    public void onInitializeClient() {
        fontRenderer = new FontUtills(Client.class.getClassLoader().getResourceAsStream("JetBrains Mono.ttf"));
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
           "MainGui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "Gui"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.isPressed()) {
                client.setScreen(new ClickScreen());
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveConfigs));
        ConfigManager.loadConfigs();
        KeybindManager.start();
        thread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(10);
                } catch (Exception ignored){

                }
                if(client.player == null || client.world == null) continue;
                KeybindManager.update();
                for(Module module : ModuleRegistry.getModules()){
                    if(module.isEnabled()) module.onFastTick();
                }
            }
        });
        thread.start();
    }
    private static KeyBinding keyBinding;
}
