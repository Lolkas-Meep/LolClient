package com.lolkas.client.funStuff.tracers;

import com.lolkas.client.Client;
import com.lolkas.client.funStuff.Module;
import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.utills.RenderUtills;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tracers extends Module {
    private boolean running = false;

    public Tracers(){
        super("Tracers");
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        running = true;
        MenuGui.TracersButton.setLabel(new LiteralText("on"));
        MenuGui.TracersLabel.setColor(Color.GREEN.getRGB());
    }

    @Override
    public void disable() {
        running = false;
        MenuGui.TracersButton.setLabel(new LiteralText("off"));
        MenuGui.TracersLabel.setColor(Color.BLACK.getRGB());
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {
        if(!running) return;
        if(Client.client.world == null || Client.client.player == null) return;
        for(PlayerEntity entity : StreamSupport.stream(Client.client.world.getPlayers().spliterator(), false).sorted(Comparator
                .comparingDouble(value -> -value.distanceTo(Client.client.player))).collect(Collectors.toList())) {
            if (entity.squaredDistanceTo(Client.client.player) > 4096) continue;
            double dc = entity.squaredDistanceTo(Client.client.player) / 4096;
            dc = Math.abs(1 - dc);
            if (entity.getUuid().equals(Client.client.player.getUuid())) continue;
            Color c = Color.WHITE;
            RenderUtills.line(RenderUtills.getCrosshairVector(), entity.getPos().add(0, entity.getHeight() / 2, 0), c, matrices);
        }
    }

    @Override
    public void toggle1() {

    }

    @Override
    public void toggle2() {

    }
}
