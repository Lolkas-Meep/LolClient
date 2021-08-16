package me.lolkas.client.gui.ClickGui;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.helper.config.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;


public class ClickScreen extends Screen {
    public static ClickScreen INSTANCE;
    public ConfigGui config = null;
    public final List<Draggable> containers = new ArrayList<>();
    String desc;

    public ClickScreen() {
        super(new LiteralText("click"));
        INSTANCE = this;
        ConfigScreen.loadState();
        int x = 20;
        for(ModuleType type: ModuleType.values()){
            if(type == ModuleType.HIDDEN) continue;
            Draggable dr = new Draggable(type.getName(), false, x, 20);
            JsonObject jsObj = ConfigScreen.get(dr.title);
            if(jsObj != null){
                dr.posX = jsObj.get("x").getAsInt();
                dr.posY = jsObj.get("y").getAsInt();
                dr.expanded = jsObj.get("expanded").getAsBoolean();
            }
            for(Module module: ModuleRegistry.getModules()){
                if(module.moduleType() == type){
                    dr.addModule(new Clickable(module));
                }
            }
            containers.add(dr);
            x = x + 95;
        }
    }

    public void openConfig(Module m){
        ConfigGui config1 = new ConfigGui(m);
        config1.x = width / 2 - config1.width / 2;
        config1.y = height / 2 - config1.height / 2;
        config = config1;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        desc("description");
        renderBackground(matrices);
        for(Draggable d: containers){
            d.render(matrices, mouseX, mouseY, delta);
        }
        if(config != null){
            config.render(matrices, mouseX, mouseY, delta);
        }
        this.textRenderer.draw(matrices, desc, client.getWindow().getScaledWidth() / 2 - this.textRenderer.getWidth(desc) / 2, client.getWindow().getScaledHeight() - 10, ClickGuiConfig.fontColor.getRGB());
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void desc(String desc){
        this.desc = desc;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for(Draggable d: containers){
            d.move(deltaX, deltaY);
        }
        if(config != null) config.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(config != null) config.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(config != null){
            if(! (config.mouseClicked(button, (int) mouseX, (int) mouseY))){
                for(Draggable d: Lists.reverse(containers).toArray(new Draggable[0])){
                    if(d.mouseClicked(button == 0, mouseX, mouseY)){
                        containers.remove(d);
                        containers.add(d);
                        return super.mouseClicked(mouseX, mouseY, button);
                    }
                    containers.remove(d);
                    containers.add(d);
                }
            }
        }else {
            for(Draggable d: Lists.reverse(containers).toArray(new Draggable[0])){
                if(d.mouseClicked(button == 0, mouseX, mouseY)){
                    containers.remove(d);
                    containers.add(d);
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                containers.remove(d);
                containers.add(d);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for(Draggable d: containers){
            d.mouseReleased();
        }
        if(config != null) config.mouseReleased((int) mouseX, (int) mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if(config != null && config.isOver((int) mouseX, (int) mouseY)) return;
        for(Draggable draggable: containers){
            draggable.mouseMove(mouseX, mouseY);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        ConfigScreen.saveState();
        return true;
    }
}
