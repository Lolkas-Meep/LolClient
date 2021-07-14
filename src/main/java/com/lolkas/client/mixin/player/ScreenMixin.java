package com.lolkas.client.mixin.player;

import com.lolkas.client.gui.AltGui;
import com.lolkas.client.gui.AltScreen;
import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.gui.MenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("RETURN"))
    private void init(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo ci) {
        Screen guiScreen = (Screen) (Object) this;

        if ((guiScreen instanceof GameMenuScreen)) {
            if(Screens.getButtons(guiScreen).get(0) != null){
                Screens.getButtons(guiScreen).add(new ButtonWidget(2, 2, 100, 20, new LiteralText("the menu"), (buttonWidget) -> {
                    if(MinecraftClient.getInstance().currentScreen != null){
                        MinecraftClient.getInstance().currentScreen.onClose();
                    }
                    MinecraftClient.getInstance().openScreen(new MenuScreen(new MenuGui()));
                }));
            }
        }
        if ((guiScreen instanceof MultiplayerScreen)){
            if(Screens.getButtons(guiScreen).get(0) != null){
                Screens.getButtons(guiScreen).add(new ButtonWidget(2, 2, 100, 20, new LiteralText("alts"), (buttonWidget) -> {
                    MinecraftClient.getInstance().openScreen(new AltScreen(new AltGui()));
                }));
            }
        }

    }
}
