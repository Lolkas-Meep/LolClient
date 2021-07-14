package com.lolkas.client.utills;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class ChatUtills {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void sendPlayerMessage(String msg){
        client.player.sendMessage(new LiteralText("§9[meep]§f " + msg), false);
    }
}
