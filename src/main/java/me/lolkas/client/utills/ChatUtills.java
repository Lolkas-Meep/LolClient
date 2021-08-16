package me.lolkas.client.utills;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ChatUtills {
    private static FontMetrics metrics;
    private static Graphics2D graphics;
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void sendPlayerMessage(String msg){
        if(client.player == null || client.world == null) return;
        client.player.sendMessage(new LiteralText("§9[meep]§f " + msg), false);
    }
    public static float getStringWidth(String text) {
        return (float) (getBounds(text).getWidth()) / 2.0F;
    }

    private static Rectangle2D getBounds(String text) {
        return metrics.getStringBounds(text, graphics);
    }
}
