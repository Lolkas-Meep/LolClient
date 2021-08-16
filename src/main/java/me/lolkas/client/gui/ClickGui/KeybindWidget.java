package me.lolkas.client.gui.ClickGui;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.config.keybind.KeybindManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class KeybindWidget extends ButtonWidget {
    MinecraftClient client = MinecraftClient.getInstance();
    final Module module;
    boolean listening = false;
    int kc;
    int sc;

    public KeybindWidget(Module module){
        super(1,1,10,10 ,Text.of(""), (buttonWidget) -> {});
        kc = (int) module.config.get("Keybind").getValue();
        this.module = module;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        listening = true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(!listening) return false;
        if (keyCode == 47 || ((char) keyCode) == '-') {
            listening = false;
            kc = -1;
            module.config.get("Keybind").setValue(kc);
            KeybindManager.reload();
            return true;
        }
        kc = keyCode;
        sc = scanCode;
        listening = false;
        module.config.get("Keybind").setValue(kc);
        KeybindManager.reload();
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        String msg;
        if(listening) msg = "... (press \"-\" to cancel)";
        else msg = kc == -1 ? "None" : GLFW.glfwGetKeyName(kc, GLFW.glfwGetKeyScancode(kc));
        fill(matrices, x, y, x + width, y + width, Color.white.getRGB());
        client.textRenderer.draw(matrices, msg, x + width + 2 , y + 2, listening ? Color.GREEN.getRGB() : ClickGuiConfig.fontColor.getRGB());
    }
}
