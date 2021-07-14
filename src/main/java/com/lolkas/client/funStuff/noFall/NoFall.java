package com.lolkas.client.funStuff.noFall;

import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.utills.ChatUtills;
import com.lolkas.client.utills.EntityUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.Executor;

public class NoFall implements ClientModInitializer {
    private enum mode{
        ALL,
        ONDEATH
    }

    private static boolean running = false;
    private static mode NoFallMode = mode.ALL;
    public static Thread NoFallThread;

    public static boolean isRunning() {
        return running;
    }

    public static boolean isEnabled(){return running;}

    public static void enable(){
        running = !running;
        if(running){
            MenuGui.NoFallLabel.setColor(0x4cff00);
            MenuGui.NoFallButton.setLabel(new LiteralText("on"));
            ChatUtills.sendPlayerMessage("NoFall enabled");
        }else{
            MenuGui.NoFallLabel.setColor(0x000000);
            MenuGui.NoFallButton.setLabel(new LiteralText("off"));
            ChatUtills.sendPlayerMessage("NoFall disabled");
        }
    }

    public static void changeMode(){
        if(NoFallMode == mode.ALL){
            NoFallMode = mode.ONDEATH;
            MenuGui.NoFallMode.setLabel(new LiteralText("On Death"));
            ChatUtills.sendPlayerMessage("NoFall mode block almost death dmg");
            ChatUtills.sendPlayerMessage("not currently working still in development");
        } else {
            NoFallMode = mode.ALL;
            MenuGui.NoFallMode.setLabel(new LiteralText("All"));
            ChatUtills.sendPlayerMessage("NoFall mode block all dmg");
        }
    }

    public void tick(MinecraftClient client){
        try {
            if(running){
                ClientPlayerEntity player = client.player;
                if(player.fallDistance <= (player.isFallFlying() ? 1 : 2))
                    return;
                if(!(player.getVelocity().y < -0.5))
                    return;

                if(NoFallMode == mode.ALL){
                    player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
                }
                if(NoFallMode == mode.ONDEATH){

                }
            }
        } catch (Exception e){

        }

    }

    public static void EnableOnDeathNoFall(){
        NoFallThread = new Thread(() -> {
            while (true){
                if (NoFallMode == mode.ONDEATH) {
                    try {
                        System.out.println("amogus");
                        Thread.sleep(50);
                    } catch (Exception e){

                    }
                }
            }
        });
        NoFallThread.start();
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        EnableOnDeathNoFall();
    }
}
