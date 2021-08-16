package me.lolkas.client.funStuff.modules;

import me.lolkas.client.funStuff.modules.config.ModuleConfig;
import me.lolkas.client.utills.ChatUtills;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Module {
    protected MinecraftClient client = MinecraftClient.getInstance();
    public final ModuleConfig config;
    private final String name;
    private boolean running = false;
    public final String help;
    public final ModuleType moduleType;

    public Module(String name, ModuleType type, String help){
        this.name = name;
        this.moduleType = type;
        this.help = help;
        this.config = new ModuleConfig();
        this.config.create("Keybind", -1);
    }
    public abstract void tick();
    public abstract void enable();
    public abstract void disable();
    public abstract void onWorldRender(MatrixStack matrices, float tickDelta);

    public String getName() {
        return name;
    }

    public void onFastTick() {

    }
    public ModuleType moduleType() {return moduleType;}
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
