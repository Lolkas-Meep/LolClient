package me.lolkas.client.gui.ClickGui;

import me.lolkas.client.funStuff.modules.config.MultiValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;

public class MultiWidget extends ButtonWidget {
    MinecraftClient client = MinecraftClient.getInstance();
    final MultiValue multi;

    public MultiWidget(MultiValue multi){
        super(1,1,10,10,new LiteralText(""), (button -> {}));
        this.multi = multi;
    }

    @Override
    public void onPress() {
        multi.cycle();
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices, x, y, x + width, y + width, Color.orange.getRGB());
        client.textRenderer.draw(matrices, multi.getValue(), x + width + 2 , y + 2, ClickGuiConfig.fontColor.getRGB());
    }
}
