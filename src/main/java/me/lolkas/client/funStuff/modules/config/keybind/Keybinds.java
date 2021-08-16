package me.lolkas.client.funStuff.modules.config.keybind;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class Keybinds {
    MinecraftClient client = MinecraftClient.getInstance();
    public final int keycode;
    boolean pressedA = false;

    public Keybinds(int keycode){
        this.keycode = keycode;
    }

    public boolean isHeld() {
        if(keycode == -1){
            return false;
        }
        return InputUtil.isKeyPressed(client.getWindow().getHandle(), keycode) && client.currentScreen == null;
    }

    public boolean isPressed(){
        if(client.currentScreen != null)
            return false;
        if(keycode == -1)
            return  false;
        boolean pressed = InputUtil.isKeyPressed(client.getWindow().getHandle(), keycode);
        if(pressed && !pressedA){
            pressedA = true;
            return true;
        }
        if(!pressed){
            pressedA = false;
        }
        return false;
    }
}
