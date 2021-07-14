package com.lolkas.client.funStuff.autoclicker;

import com.lolkas.client.funStuff.autoclicker.AutoClicker;
import com.lolkas.client.gui.MenuGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class AutoClickerMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }

    private void tick(MinecraftClient client) {
        AutoClicker.updateLabel();
        try {
            if (AutoClicker.running) {
                if (AutoClicker.mode == 1) {
                    switch (AutoClicker.button) {
                        case LEFT:
                            MinecraftClient.getInstance().options.keyAttack.setPressed(true);
                            break;
                        case RIGHT:
                            MinecraftClient.getInstance().options.keyUse.setPressed(true);
                            break;
                    }
                }
                if (AutoClicker.mode == 2) {
                    AutoClicker.timer++;
                    if (AutoClicker.timer == AutoClicker.Timer) {
                        if (AutoClicker.button == AutoClicker.Button.LEFT) {
                            AutoClicker.leftClick();
                        } else {
                            AutoClicker.rightClick();
                        }
                        AutoClicker.timer = 0;
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
