package me.lolkas.client.gui.ClickGui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class Draggable {
    MinecraftClient client = MinecraftClient.getInstance();
    public final String title;
    final List<Clickable> modules = new ArrayList<>();
    public boolean expanded;
    public boolean dragged;
    public double posX = 20;
    public double posY = 20;


    public Draggable(String title, boolean expanded, double posX, double posY){
        this.title = title;
        this.expanded = expanded;
        this.posX = posX;
        this.posY = posY;
    }

    public void addModule(Clickable module){
        this.modules.add(module);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, (int) this.posX, (int) this.posY,(int) this.posX + ClickGuiConfig.Margin + 85, (int) this.posY + ClickGuiConfig.Margin + 9, ClickGuiConfig.titleColor.getRGB());
        //border
        DrawableHelper.fill(matrices, (int) this.posX, (int) this.posY,(int) this.posX + ClickGuiConfig.Margin + 85, (int) this.posY + 1, ClickGuiConfig.borderColor.getRGB());
        DrawableHelper.fill(matrices, (int) this.posX, (int) this.posY,(int) this.posX + 1, (int) this.posY + ClickGuiConfig.Margin + 9, ClickGuiConfig.borderColor.getRGB());
        DrawableHelper.fill(matrices, (int) this.posX + ClickGuiConfig.Margin + 85, (int) this.posY + ClickGuiConfig.Margin + 9,(int) this.posX, (int) this.posY + ClickGuiConfig.Margin + 9 - 1, ClickGuiConfig.borderColor.getRGB());
        DrawableHelper.fill(matrices, (int) this.posX + ClickGuiConfig.Margin + 85, (int) this.posY + ClickGuiConfig.Margin + 9,(int) this.posX + ClickGuiConfig.Margin + 85 - 1, (int) this.posY, ClickGuiConfig.borderColor.getRGB());
        client.textRenderer.draw(matrices, this.title, (int) this.posX + ClickGuiConfig.Margin, (int) this.posY + ClickGuiConfig.Margin, ClickGuiConfig.fontColor.getRGB());
        if(expanded){
            double modY = this.posY;
            for (Clickable clickable: modules){
                modY += ClickGuiConfig.Margin + 9;
                clickable.render(matrices, posX, modY);
            }
        }
    }

    public void move(double deltaX, double deltaY){
        if(this.dragged){
            this.posX = deltaX + this.posX;
            this.posY = deltaY + this.posY;
        }
    }

    public void mouseReleased(){
        this.dragged = false;
    }

    public boolean mouseClicked(boolean left, double x, double y){
        if(this.posX <= x && this.posX + ClickGuiConfig.Margin + 85 >= x && this.posY <= y && this.posY + ClickGuiConfig.Margin + 9 >= y){
            if(left){
                this.dragged = true;
                return true;
            }else {
                this.expanded = !this.expanded;
            }
        } else {
            this.dragged = false;
            if(this.expanded){
                double modY = this.posY;
                for (Clickable clickable: modules){
                    modY += ClickGuiConfig.Margin + 9;
                    if(this.posX <= x && this.posX + ClickGuiConfig.Margin + 85 >= x && modY <= y && modY + ClickGuiConfig.Margin + 9 >= y){
                        clickable.clicked(left);
                    }
                }
            }
        }
        return false;
    }

    public void mouseMove(double mouseX, double mouseY){
        for(Clickable clickable: modules){
            clickable.mouseOver((int) mouseX, (int) mouseY);
        }
    }
}
