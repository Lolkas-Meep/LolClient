package me.lolkas.client.funStuff.modules.smallMods.hud;

import net.minecraft.client.MinecraftClient;

public class coords {
    private static int x;
    private static int y;
    private static int z;
    private static MinecraftClient client;

    public static void start(){
        //client = MinecraftClient.getInstance();
        //HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
        //    client.textRenderer.drawWithShadow(matrices, "X: " + x + " Y: " + y +" Z:" + z, 15,20,5592575);
        //   client.textRenderer.drawWithShadow(matrices, "Fps: " + ((IMinecraftClient) client).getFps(), 15, 10, 5592575);
        //});
    }

    public static void renderText(){
        try {
            x = (int) client.player.getX();
            y = (int) client.player.getY();
            z = (int) client.player.getZ();
        }catch (Exception e){

        }
    }
}
