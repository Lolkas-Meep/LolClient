package me.lolkas.client.funStuff.modules.render;

import me.lolkas.client.Client;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.utills.RenderUtills;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tracers extends Module {

    public Tracers(){
        super("Tracers", ModuleType.RENDER, "lines");
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {
        if(Client.client.world == null || Client.client.player == null) return;
        for(PlayerEntity entity : StreamSupport.stream(Client.client.world.getPlayers().spliterator(), false).sorted(Comparator
                .comparingDouble(value -> -value.distanceTo(Client.client.player))).collect(Collectors.toList())) {
            if (entity.squaredDistanceTo(Client.client.player) > 4096) continue;
            if (entity.getUuid().equals(Client.client.player.getUuid())) continue;
            Color c = Color.WHITE;
            RenderUtills.line(RenderUtills.getCrosshairVector(), entity.getPos().add(0, entity.getHeight() / 2, 0), c, matrices);
        }
    }
}
