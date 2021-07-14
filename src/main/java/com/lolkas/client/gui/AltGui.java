package com.lolkas.client.gui;

import com.lolkas.client.funStuff.smallMods.alt.Alt;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;

public class AltGui extends LightweightGuiDescription {

    public static WLabel AltLabel = new WLabel(new LiteralText("Offline Account"));
    public static WTextField AltText = new WTextField(new LiteralText("Name"));
    public static WLabel AltDisplay = new WLabel(new LiteralText("currently logged in as "));
    public static WButton AltButton = new WButton(new LiteralText("Enter")).setOnClick(() -> {
        Alt.onEnable();
        Alt.setUser(AltText.getText());
        AltDisplay.setText(new LiteralText("currently logged in as " + AltText.getText()));
    });

    public AltGui(){
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(255,200);

        root.add(AltLabel, 1,1,5,1);
        root.add(AltText,1,2,5,1);
        root.add(AltButton,1,4,5,1);
        root.add(AltDisplay, 1, 6,5,1);
    }
}
