package me.lolkas.client.funStuff.modules.combat;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.MultiValue;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import me.lolkas.client.utills.PlayerUtills;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class AutoClicker extends Module {
    public int timer;

    final MultiValue button = (MultiValue) this.config.create("Button", "Left", "Left", "Right");
    final MultiValue isClicking = (MultiValue) this.config.create("Mode", "Hold", "Hold", "Click");
    final SliderValue delay = (SliderValue) this.config.create("delay", 20, 2, 200, 0);

    public AutoClicker() {
        super("AutoClicker", ModuleType.COMBAT, "it clicks lmao");
    }


    @Override
    public void tick() {
        if(client.world == null || client.player == null) return;
        if (isClicking.getValue().equals("Hold")) {
            if(button.getValue().equals("Left")){
                client.options.keyAttack.setPressed(true);
            }else {
                client.options.keyUse.setPressed(true);
            }
        } else  {
            timer++;
            if (timer >= delay.getValue()) {
                if (button.getValue().equals("Left")) {
                    PlayerUtills.leftClick();
                } else {
                    PlayerUtills.rightClick();
                }
                timer = 0;
            }
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {
        MinecraftClient.getInstance().options.keyUse.setPressed(false);
        MinecraftClient.getInstance().options.keyAttack.setPressed(false);
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {

    }
}
