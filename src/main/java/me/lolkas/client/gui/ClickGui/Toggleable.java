package me.lolkas.client.gui.ClickGui;

import me.lolkas.client.funStuff.modules.config.BooleanValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

//Skidded from atomic https://github.com/cornos/Atomic

public class Toggleable extends ButtonWidget {
    final MinecraftClient client = MinecraftClient.getInstance();
    final BooleanValue parent;

    public Toggleable(int x, int y, int width, BooleanValue parent){
        super(x, y, width, 12, Text.of(parent.getValue() ? "Enabled" : "Disabled"), (buttonWidget) -> {
        });
        this.parent = parent;
        this.width = 10;
    }

    @Override
    public void onPress() {
        parent.setValue(!parent.getValue());
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.setMessage(Text.of(parent.getValue() ? "Enabled" : "Disabled"));
        fill(matrices, x, y, x + width, y + width, this.parent.getValue() ? ClickGuiConfig.activeColor.getRGB() : ClickGuiConfig.inactiveColor.getRGB());
        client.textRenderer.draw(matrices, parent.getKey(), x + width + 2 , y + 2, ClickGuiConfig.fontColor.getRGB());
    }
}
