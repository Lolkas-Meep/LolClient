package me.lolkas.client.funStuff.modules.smallMods.save;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.utills.ChatUtills;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.GameMode;

public class Save {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Module module;

    public static void tick() {
        if (client.player.getY() < 0 && !ModuleRegistry.getByName("Flight").isEnabled()) {
            module = ModuleRegistry.getByName("Flight");
            PlayerListEntry playerListEntry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            if (playerListEntry.getGameMode() == GameMode.SURVIVAL) {
                module.setEnabled(true);
                ChatUtills.sendPlayerMessage("saved ur ass");
            }
        }
    }
}
