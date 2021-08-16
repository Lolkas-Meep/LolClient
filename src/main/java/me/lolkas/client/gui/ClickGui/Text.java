package me.lolkas.client.gui.ClickGui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class Text extends ClickableWidget {
    final MinecraftClient client = MinecraftClient.getInstance();
    int posX;
    int posY;
    String value;

    public Text(int x, int y, String value){
        super(1,1,1,1,new LiteralText(""));
        this.posX = x;
        this.posY = y;
        this.value = value;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        client.textRenderer.draw(matrices, value, x, y, ClickGuiConfig.fontColor.getRGB());
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}
