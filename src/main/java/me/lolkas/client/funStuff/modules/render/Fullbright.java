package me.lolkas.client.funStuff.modules.render;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class Fullbright extends Module {
    final SliderValue gamma = this.config.create("Gamma", 16, 1, 16, 1);
    double otherGamma;

    public Fullbright(){
        super("Fullbright", ModuleType.RENDER, "makes it bright");
    }

    @Override
    public void tick() {
        if(client.options == null) return;
        client.options.gamma = gamma.getValue();
    }

    @Override
    public void enable() {
        otherGamma = MathHelper.clamp(client.options.gamma, 0, 1);
    }

    @Override
    public void disable() {
        client.options.gamma = otherGamma;
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {

    }
}
