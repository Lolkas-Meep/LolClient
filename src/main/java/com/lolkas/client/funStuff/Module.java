package com.lolkas.client.funStuff;

import com.lolkas.client.utills.ChatUtills;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Module {
    private final String name;
    private boolean running = false;

    public Module(String name){
        this.name = name;
    }
    public abstract void tick();
    public abstract void enable();
    public abstract void disable();
    public abstract void onWorldRender(MatrixStack matrices, float tickDelta);
    public abstract void toggle1();
    public abstract void toggle2();

    public String getName() {
        return name;
    }

    public void onFastTick() {

    }
    public void toggle() {
        setEnabled(!running);
    }
    public boolean isEnabled() {
        return running;
    }
    public void setEnabled(boolean running) {
        this.running = running;
        ChatUtills.sendPlayerMessage(name + (this.running ? " En" : " Dis") + "abled");
        if (this.running) this.enable();
        else this.disable();
    }
}
