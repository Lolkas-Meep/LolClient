package com.lolkas.client.funStuff.smallMods.save;

import com.lolkas.client.funStuff.flight.Flight;
import com.lolkas.client.utills.ChatUtills;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.GameMode;

public class Save {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void tick() {
        if (client.player.getY() < 0 && !Flight.isEnabled()) {
            PlayerListEntry playerListEntry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            if (playerListEntry.getGameMode() == GameMode.SURVIVAL) {
                Flight.enable();
                ChatUtills.sendPlayerMessage("saved ur ass");
            }
        }
    }
}
