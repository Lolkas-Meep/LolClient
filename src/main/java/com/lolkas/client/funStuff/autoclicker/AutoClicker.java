package com.lolkas.client.funStuff.autoclicker;

import com.lolkas.client.mixin.player.IMinecraftClient;
import com.lolkas.client.gui.MenuGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class AutoClicker {
    public static int mode = 0;
    public static boolean running = false;
    public static Button button = Button.LEFT;
    public static int Timer;
    public static int timer;

    public enum Button{
        LEFT,
        RIGHT
    }

    static public void SetButton(){
        if(button == Button.LEFT){
            MenuGui.ClickerType.setLabel(new LiteralText("Right"));
            button = Button.RIGHT;
        }else {
            MenuGui.ClickerType.setLabel(new LiteralText("Left"));
            button = Button.LEFT;
        }
    }

    static public void SetMode(){
        mode++;
        if(mode > 2){
            mode = 0;
        }
        if(mode == 0){
            MenuGui.Clicker.setLabel(new LiteralText("Off"));
            MenuGui.ClickerLabel.setColor(0x000000);
            MinecraftClient.getInstance().options.keyUse.setPressed(false);
            MinecraftClient.getInstance().options.keyAttack.setPressed(false);
            running = false;
        }
        if(mode == 1){
            MenuGui.Clicker.setLabel(new LiteralText("Hold"));
            MenuGui.ClickerLabel.setColor(0x4cff00);
            running = true;
        }
        if(mode == 2){
            MenuGui.Clicker.setLabel(new LiteralText("Click"));
            MenuGui.ClickerLabel.setColor(0x4cff00);
            timer = 0;
            running = true;
        }
    }
    static public void updateLabel(){
        Timer = MenuGui.ClickerSlider.getValue();
        MenuGui.ClickerSlider.setLabel(new LiteralText(Timer + " tick/s"));
    }

    static public void rightClick(){
        ((IMinecraftClient) MinecraftClient.getInstance()).rightClick();
    }

    static public void leftClick(){
        MinecraftClient.getInstance().options.keyAttack.setPressed(true);
        ((IMinecraftClient) MinecraftClient.getInstance()).leftClick();
        MinecraftClient.getInstance().options.keyAttack.setPressed(false);
    }


}
