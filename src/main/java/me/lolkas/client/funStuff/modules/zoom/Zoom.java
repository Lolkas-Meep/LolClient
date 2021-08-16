package me.lolkas.client.funStuff.modules.zoom;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Zoom implements ClientModInitializer {
    private static double normalFov;
    private static double Fov;

    KeyBinding zoom = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "Client Hotkeys"
    ));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        ClientLifecycleEvents.CLIENT_STARTED.register(this::start);
    }

    private void start(MinecraftClient client){
        normalFov = client.options.fov;
        Fov = normalFov;
    }

    private void tick(MinecraftClient client) {
        try {
                if(zoom.isPressed()){
                    Fov = normalFov / 3;
                }
                if(!zoom.isPressed()){

                    Fov = normalFov;
                }
            MinecraftClient.getInstance().options.fov = Fov;
        }catch (Exception e){

        }
    }
}
