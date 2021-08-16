package me.lolkas.client.gui.ClickGui;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.config.BooleanValue;
import me.lolkas.client.funStuff.modules.config.DynamicValue;
import me.lolkas.client.funStuff.modules.config.MultiValue;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Skidded from atomic https://github.com/cornos/Atomic

public class ConfigGui {
    final MinecraftClient client = MinecraftClient.getInstance();
    public final Module module;
    final Map<DynamicValue<?>, List<ClickableWidget>> childs = new LinkedHashMap<>();
    boolean dragged;
    boolean clicked = false;
    public int x;
    public int y;
    int width = 120;
    int height = 180;

    public ConfigGui(Module module){
        this.module = module;
        
        List<DynamicValue<?>> dy = module.config.getConfig();
        DynamicValue<?> key = null;
        List<ClickableWidget> binds = new ArrayList<>();
        
        for (DynamicValue<?> dynamicValue : dy){
            List<ClickableWidget> cw = new ArrayList<>();
            if(dynamicValue instanceof BooleanValue){
                Toggleable t = new Toggleable(1, 1, 100, (BooleanValue) dynamicValue);
                cw.add(t);
                childs.put(dynamicValue, cw);
            }else if(dynamicValue instanceof MultiValue){
                Text t = new Text(1,1, dynamicValue.getKey());
                MultiWidget m = new MultiWidget((MultiValue) dynamicValue);
                cw.add(t);
                cw.add(m);
                childs.put(dynamicValue, cw);
            }else if(dynamicValue instanceof SliderValue){
                Text t = new Text(1,1, dynamicValue.getKey());
                Slider s = new Slider(1, 1, 1, (SliderValue) dynamicValue);
                cw.add(t);
                cw.add(s);
                childs.put(dynamicValue, cw);
            } else if(dynamicValue.getKey().equalsIgnoreCase("Keybind")){
                Text t = new Text(1,1,"Keybind");
                KeybindWidget k = new KeybindWidget(module);
                binds.add(t);
                binds.add(k);
                key = dynamicValue;
            }
        }
        childs.put(key, binds);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta){
        int renderY;
        Color fillColor = ClickGuiConfig.inactiveColor;
        Color fontColor = ClickGuiConfig.fontColor;
        DrawableHelper.fill(matrices, x, y, x + width + ClickGuiConfig.Margin, y + height + ClickGuiConfig.Margin, fillColor.getRGB());
        DrawableHelper.fill(matrices, x + width - 6, y + ClickGuiConfig.Margin, x + width, y + ClickGuiConfig.Margin - 2, Color.red.getRGB());
        client.textRenderer.draw(matrices, module.getName(), x + ClickGuiConfig.Margin, y + ClickGuiConfig.Margin, fontColor.getRGB());
        renderY = y + ClickGuiConfig.Margin + client.textRenderer.fontHeight + 2;
        client.textRenderer.draw(matrices, module.help, x + ClickGuiConfig.Margin, renderY, fontColor.getRGB());
        renderY += client.textRenderer.fontHeight + 2 + 10;
        List<DynamicValue<?>> d = new ArrayList<>(childs.keySet());
        for(DynamicValue<?> chile : d){
            if(!chile.shouldShow()) continue;
            List<ClickableWidget> children = this.childs.get(chile);
            for(ClickableWidget child : children){
                child.x = x + ClickGuiConfig.Margin;
                child.y = renderY;
                child.render(matrices, mouseX, mouseY, delta);
                if(child instanceof Text) renderY += 10;
                else renderY += 14;
            }
        }
    }

    public boolean mouseClicked(int button, int mX, int mY){
        boolean ret = false;

        if(x <= mX && x + width + ClickGuiConfig.Margin >= mX && y <= mY && y + height + ClickGuiConfig.Margin >= mY){
            if(button == 0){
                dragged = true;
            }
            clicked = true;
            ret = true;
        }else {
            dragged = false;
            clicked = false;
        }
        for (List<ClickableWidget> childrens : this.childs.values()){
            for (ClickableWidget child : childrens){
                child.mouseClicked(mX, mY, button);
            }
        }
        return ret;
    }
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY){
        if (button == 0){
            if(dragged){
                x = (int) (x + deltaX);
                y = (int) (y + deltaY);
            }
        }
        for (List<ClickableWidget> childrens : this.childs.values()){
            for (ClickableWidget child : childrens){
                child.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
        }
    }
    public void mouseReleased(int mX, int mY){
        if(clicked && x + width - 8 <= mX && x + width >= mX && y + ClickGuiConfig.Margin - 4 <= mY && y + ClickGuiConfig.Margin >= mY){
            ClickScreen.INSTANCE.config = null;
        }else {
            clicked = false;
        }
        for (List<ClickableWidget> childrens : this.childs.values()){
            for (ClickableWidget child : childrens){
                child.mouseReleased(mX, mY, -1);
            }
        }
    }
    public boolean isOver(int mX, int mY){
        boolean rtn = false;
        if (x <= mX && x + width + ClickGuiConfig.Margin >= mX && y <= mY && y + height + ClickGuiConfig.Margin >= mY){
            rtn = true;
        }
        for (List<ClickableWidget> childrens : this.childs.values()){
            for (ClickableWidget child : childrens){
                child.mouseMoved(mX, mY);
            }
        }
        return rtn;
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (List<ClickableWidget> childrens : this.childs.values()){
            for (ClickableWidget child : childrens){
                child.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }
}
