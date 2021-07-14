package com.lolkas.client.funStuff.speed;

import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.utills.ChatUtills;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

public class Speed implements ClientModInitializer {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static boolean running = false;

    public static void enable(){
        running = !running;
        if(running){
            MenuGui.SpeedLabel.setColor(0x4cff00);
            MenuGui.SpeedButton.setLabel(new LiteralText("on"));
            ChatUtills.sendPlayerMessage("Speed Enabled");
        }else {
            MenuGui.SpeedLabel.setColor(0x000000);
            MenuGui.SpeedButton.setLabel(new LiteralText("off"));
            client.player.setSprinting(false);
            ChatUtills.sendPlayerMessage("Speed Disabled");
        }
    }
    public void tick(MinecraftClient client){
        try {
            if(running){
                if(client.player.isSneaking() || client.player.forwardSpeed == 0 && client.player.sidewaysSpeed == 0)
                    return;
                if(client.player.forwardSpeed > 0 && !client.player.horizontalCollision)
                    client.player.setSprinting(true);
                if(!client.player.isOnGround())
                    return;

                Vec3d vel = client.player.getVelocity();
                client.player.setVelocity(vel.x * 1.8, vel.y + 0.1, vel.z * 1.8);
                vel = client.player.getVelocity();
                double currentSpeed = Math.sqrt(Math.pow(vel.x, 2) + Math.pow(vel.z, 2));

                double maxSpeed = 0.66F;

                if(currentSpeed > maxSpeed)
                    client.player.setVelocity(vel.x / currentSpeed * maxSpeed, vel.y,
                            vel.z / currentSpeed * maxSpeed);
            }
        } catch (Exception e){

        }
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }
}
