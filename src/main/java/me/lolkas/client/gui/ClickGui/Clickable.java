package me.lolkas.client.gui.ClickGui;

import me.lolkas.client.funStuff.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class Clickable {
    final Module module;
    int mouseX;
    int mouseY;
    MinecraftClient client = MinecraftClient.getInstance();

    public Clickable(Module module){
        this.module = module;
    }

    public void render(MatrixStack matrices, double x, double y){
        Color fontColor = ClickGuiConfig.fontColor;
        Color inactiveColor = ClickGuiConfig.inactiveColor;
        Color activeColor = ClickGuiConfig.activeColor;
        Color borderIn = ClickGuiConfig.borderInactiveColor;
        Color borderAc = ClickGuiConfig.borderActiveColor;
        int margin = ClickGuiConfig.Margin;
        if(module.isEnabled()){
            DrawableHelper.fill(matrices, (int) x, (int) y, (int) x + margin + 85, (int) y + margin + 9, activeColor.getRGB());
        }else {
            DrawableHelper.fill(matrices, (int) x, (int) y, (int) x + margin + 85, (int) y + margin + 9, inactiveColor.getRGB());
        }
        client.textRenderer.draw(matrices, module.getName(), (int) x + margin, (int) y + margin, fontColor.getRGB());
        if(x <= mouseX && x + margin + 85 >= mouseX && y <= mouseY && y + margin + 9 >= mouseY){
            if(module.isEnabled()){
                DrawableHelper.fill(matrices, (int) x, (int) y,(int) x + ClickGuiConfig.Margin + 85, (int) y + 1, borderAc.getRGB());
                DrawableHelper.fill(matrices, (int) x, (int) y,(int) x + 1, (int) y + ClickGuiConfig.Margin + 9, borderAc.getRGB());
                DrawableHelper.fill(matrices, (int) x + ClickGuiConfig.Margin + 85, (int) y + ClickGuiConfig.Margin + 9,(int) x, (int) y + ClickGuiConfig.Margin + 9 - 1, borderAc.getRGB());
                DrawableHelper.fill(matrices, (int) x + ClickGuiConfig.Margin + 85, (int) y + ClickGuiConfig.Margin + 9,(int) x + ClickGuiConfig.Margin + 85 - 1, (int) y, borderAc.getRGB());
            }else {
                DrawableHelper.fill(matrices, (int) x, (int) y,(int) x + ClickGuiConfig.Margin + 85, (int) y + 1, borderIn.getRGB());
                DrawableHelper.fill(matrices, (int) x, (int) y,(int) x + 1, (int) y + ClickGuiConfig.Margin + 9, borderIn.getRGB());
                DrawableHelper.fill(matrices, (int) x + ClickGuiConfig.Margin + 85, (int) y + ClickGuiConfig.Margin + 9,(int) x, (int) y + ClickGuiConfig.Margin + 9 - 1, borderIn.getRGB());
                DrawableHelper.fill(matrices, (int) x + ClickGuiConfig.Margin + 85, (int) y + ClickGuiConfig.Margin + 9,(int) x + ClickGuiConfig.Margin + 85 - 1, (int) y, borderIn.getRGB());
            }
        }
    }

    public void clicked(boolean left){
        if(left){
            this.module.toggle();
        }else {
            ClickScreen.INSTANCE.openConfig(module);
        }
    }

    public void mouseOver(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
