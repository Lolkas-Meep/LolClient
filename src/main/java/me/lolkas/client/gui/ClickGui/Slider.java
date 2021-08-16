package me.lolkas.client.gui.ClickGui;

import me.lolkas.client.funStuff.modules.config.SliderValue;
import me.lolkas.client.utills.MiscUtills;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

public class Slider extends ClickableWidget {
    final MinecraftClient client = MinecraftClient.getInstance();
    final double min;
    final double max;
    final SliderValue slider;
    final int prec;
    double sliderX;
    boolean dragged = false;


    public Slider(int x, int y, int width , SliderValue sv){
        super(x, y, width, 6, new LiteralText(sv.getKey()));
        this.max = sv.getMax();
        this.min = sv.getMin();
        this.prec = sv.getPrec();
        this.slider = sv;
        height = 10;
        sliderX = (90 * (slider.getValue() / (max - min)));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        slider.setValue(MiscUtills.roundToN((max - min) * (sliderX / 90), prec));
        client.textRenderer.draw(matrices, slider.getValue() + "", x, y, ClickGuiConfig.fontColor.getRGB());
        //slider large rectangle thing
        fill(matrices, x + 25, y + height / 3, x + 90 + 25, y + height * 2 / 3, ClickGuiConfig.inactiveColor.getRGB());
        fill(matrices, (int) (x + 25 + sliderX), y + height / 3 - height / 2, (int) (x + 25 + sliderX + 4), y + height * 2 / 3 + height / 2, ClickGuiConfig.titleColor.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(x + 25 + sliderX <= mouseX && x + 25 + sliderX + 4 >= mouseX && y + height / 3 - height / 2 <= mouseY && y + height * 2 / 3 + height / 2 >= mouseY){
            this.dragged = true;
        }
        else {
            this.dragged = false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        this.dragged = false;
        super.onRelease(mouseX, mouseY);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if(dragged){
            sliderX = MathHelper.clamp(sliderX + deltaX, 0, 90 + 1);
            ClickScreen.INSTANCE.config.dragged = false;
        }
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}
