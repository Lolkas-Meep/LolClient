package com.lolkas.client.funStuff.esp;

import com.lolkas.client.Client;
import com.lolkas.client.funStuff.Module;
import com.lolkas.client.gui.MenuGui;
import com.lolkas.client.utills.RenderUtills;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ESP extends Module {
    private boolean running = false;
    private int mode = 0;
    private boolean ESPplayer = false;
    private boolean ESPentities = false;
    private VertexBuffer mobBox;

    public ESP() {
        super("ESP");
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        running = true;
        MenuGui.ESPLabel.setColor(Color.GREEN.getRGB());
        MenuGui.ESPButton.setLabel(new LiteralText("on"));
        mobBox = new VertexBuffer();
    }

    @Override
    public void disable() {
        running = false;
        MenuGui.ESPLabel.setColor(Color.BLACK.getRGB());
        MenuGui.ESPButton.setLabel(new LiteralText("off"));
        if(mobBox != null)
            mobBox.close();
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {
        if(!running) return;
        if(Client.client.world == null || Client.client.player == null) return;
        for (Entity entity : Client.client.world.getEntities()){
            if (entity.squaredDistanceTo(Client.client.player) > 4096) continue;
            if (entity.getUuid().equals(Client.client.player.getUuid())) continue;

            Color color;
            if(entity instanceof PlayerEntity) color = Color.RED;
            else if(entity instanceof ItemEntity) color = Color.CYAN;
            else if(entity instanceof HostileEntity) color = Color.YELLOW;
            else color = Color.GREEN;

            if((entity instanceof PlayerEntity && ESPplayer) || ESPentities){
                RenderUtills.renderOutline(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), color, matrices);
            }
        }
    }
    @Override
    public void toggle1() {
        mode++;
        if(mode == 0 || mode == 3){
            mode = 0;
            ESPplayer = true;
            ESPentities = false;
            MenuGui.ESPMode.setLabel(new LiteralText("player"));
        }else if(mode == 1){
            mode = 1;
            ESPplayer = false;
            ESPentities = true;
            MenuGui.ESPMode.setLabel(new LiteralText("entity"));
        }else if (mode == 2){
            ESPplayer = true;
            ESPentities = true;
            MenuGui.ESPMode.setLabel(new LiteralText("both"));
        }
    }

    @Override
    public void toggle2() {

    }
}
