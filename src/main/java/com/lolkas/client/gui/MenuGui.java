package com.lolkas.client.gui;

import com.lolkas.client.funStuff.Module;
import com.lolkas.client.funStuff.ModuleRegistry;
import com.lolkas.client.funStuff.autoclicker.AutoClicker;
import com.lolkas.client.funStuff.flight.Flight;
import com.lolkas.client.funStuff.noFall.NoFall;
import com.lolkas.client.funStuff.speed.Speed;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.text.LiteralText;

import java.awt.*;

public class MenuGui extends LightweightGuiDescription {
    private static Module module;

    public static WButton Clicker = new WButton(new LiteralText("Off")).setOnClick(() -> {
        AutoClicker.SetMode();
    });
    public static WButton ClickerType = new WButton(new LiteralText("Left")).setOnClick(() ->{
        AutoClicker.SetButton();
    });
    public static WLabel ClickerLabel = new WLabel(new LiteralText("AutoClicker"), 0x000000);
    public static WLabeledSlider ClickerSlider = new WLabeledSlider(2, 60, new LiteralText("1 tick/s"));

    public static WLabel GammaLabel = new WLabel(new LiteralText("Gamma"), 0x000000);
    public static WLabeledSlider GammaSlider = new WLabeledSlider(1, 20);

    public static WLabel SpeedLabel = new WLabel(new LiteralText("Speed"), 0x000000);
    public static WButton SpeedButton = new WButton(new LiteralText("off")).setOnClick(() -> {
        Speed.enable();
    });

    public static WLabel NoFallLabel = new WLabel(new LiteralText("NoFall"), 0x000000);
    public static WButton NoFallButton = new WButton(new LiteralText("off")).setOnClick(() -> {
        NoFall.enable();
    });

    public static WButton NoFallMode = new WButton(new LiteralText("All")).setOnClick(() -> {
        NoFall.changeMode();
    });
    public static WLabel FlightLabel = new WLabel(new LiteralText("Flight"), 0x000000);
    public static WButton FlightButton = new WButton(new LiteralText("off")).setOnClick(() -> {
        Flight.enable();
    });
    public static WLabeledSlider FlightSlider = new WLabeledSlider(1,5, new LiteralText("1"));

    public static WLabel TracersLabel = new WLabel(new LiteralText("Tracers"), 0x000000);
    public static WButton TracersButton = new WButton(new LiteralText("off")).setOnClick(() -> {
        module = ModuleRegistry.getByName("Tracers");
        module.toggle();
    });

    public static WLabel ESPLabel = new WLabel(new LiteralText("ESP"), Color.BLACK.getRGB());
    public static WButton ESPMode = new WButton(new LiteralText("player")).setOnClick(() -> {
        module = ModuleRegistry.getByName("ESP");
        module.toggle1();
    });
    public static WButton ESPChest = new WButton(new LiteralText("chest off")).setOnClick(() -> {
        module = ModuleRegistry.getByName("ChestESP");
        module.toggle();
    });
    public static WButton ESPButton = new WButton(new LiteralText("off")).setOnClick(() -> {
        module = ModuleRegistry.getByName("ESP");
        module.toggle();
    });

    public MenuGui(){
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(350, 240);

        root.add(ClickerLabel, 1, 1, 5, 1);
        root.add(Clicker, 1,2,3,1);
        root.add(ClickerType, 4, 2, 2, 1);
        root.add(ClickerSlider, 1, 3, 5,1);

        root.add(GammaLabel, 1, 5, 5, 1);
        root.add(GammaSlider, 1, 6, 5, 1);

        root.add(SpeedLabel, 1, 8, 5, 1);
        root.add(SpeedButton,1, 9, 5,1);

        root.add(NoFallLabel, 1, 11, 5, 1);
        root.add(NoFallButton, 1, 12, 3, 1);
        root.add(NoFallMode, 4,12,2,1);

        root.add(FlightLabel, 8, 1, 5,1);
        root.add(FlightButton, 8,2,5,1);
        root.add(FlightSlider, 8,3, 5,1);

        root.add(TracersLabel, 8,5,5,1);
        root.add(TracersButton, 8,6,5,1);

        root.add(ESPLabel, 8,8,5,1);
        root.add(ESPChest, 8,9,5,1);
        root.add(ESPButton, 8,10,3,1);
        root.add(ESPMode, 11, 10,2,1);
    }
}
