package com.lolkas.client.funStuff.flight;

import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.utills.ChatUtills;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Flight implements ClientModInitializer {
    private static boolean running = false;
    private static boolean pressed = false;
    private static int speed;

    private static KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Flight",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_1,
            "Client Hotkeys"
    ));

    public static void enable(){
        running = !running;
        if(running){
            MenuGui.FlightLabel.setColor(0x4cff00);
            MenuGui.FlightButton.setLabel(new LiteralText("on"));
            ChatUtills.sendPlayerMessage("Flight Enabled");
        } else {
            MenuGui.FlightLabel.setColor(0x000000);
            MenuGui.FlightButton.setLabel(new LiteralText("off"));
            ChatUtills.sendPlayerMessage("Flight Disabled");
        }
    }

    public static boolean isEnabled(){return  running;}

    public void tick(MinecraftClient client){
        if(keyBinding.isPressed() && !pressed){
            pressed = true;
            enable();
        }
        if (!keyBinding.isPressed() && pressed){
            pressed = false;
        }
        try{
            if(!running)
                return;

            ClientPlayerEntity player = client.player;

            speed = MenuGui.FlightSlider.getValue();
            MenuGui.FlightSlider.setLabel(new LiteralText(Integer.toString(speed)));

            player.getAbilities().flying = false;
            player.flyingSpeed = speed;

            player.setVelocity(0, 0, 0);
            Vec3d velocity = player.getVelocity();

            if(client.options.keyJump.isPressed())
                player.setVelocity(velocity.add(0, speed, 0));

            if(client.options.keySneak.isPressed())
                player.setVelocity(velocity.subtract(0, speed, 0));
        } catch (Exception e){

        }
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }
}
