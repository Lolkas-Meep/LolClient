package me.lolkas.client.funStuff.modules.smallMods.deathCoords;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class deathCoordsMain implements ClientModInitializer {
    private static boolean died = false;

    public void tick(MinecraftClient client){
        try {
            if(client.player.getHealth() == 0 && !died){
                died = true;
            }
            if(client.player.getHealth() == 0 && died){
                client.player.sendMessage(new LiteralText("§9[meep]§f bruh u died lmao!" + " coords: " + (int) client.player.getX() + " " + (int) client.player.getY() + " " + (int) client.player.getZ()), false);
                died = false;
            }else{
                died = false;
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }
}
