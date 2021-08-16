package me.lolkas.client.funStuff.modules.render;

import me.lolkas.client.Client;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.BooleanValue;
import me.lolkas.client.funStuff.modules.config.MultiValue;
import me.lolkas.client.utills.RenderUtills;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ESP extends Module {
    final MultiValue mode = this.config.create("mode", "Outline", "Outline", "Fill");
    final BooleanValue player = this.config.create("Player", true);
    final BooleanValue item = this.config.create("Item", false);
    final BooleanValue hostile = this.config.create("Hostile", false);
    final BooleanValue passive = this.config.create("Passive", false);

    public ESP() {
        super("ESP", ModuleType.RENDER, "colored glowing");
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
        for (Entity entity : Client.client.world.getEntities()){
            if (entity.squaredDistanceTo(Client.client.player) > 4096) continue;
            if (entity.getUuid().equals(Client.client.player.getUuid())) continue;

            if(mode.getValue().equals("Outline")){
                if(entity instanceof PlayerEntity && player.getValue()){
                    RenderUtills.renderOutline(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(255, 0, 0, 153), matrices);
                }
                if(entity instanceof ItemEntity && item.getValue()){
                    RenderUtills.renderOutline(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(0, 255, 255, 153), matrices);
                }
                if(entity instanceof HostileEntity && hostile.getValue()){
                    RenderUtills.renderOutline(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(255, 255, 0, 153), matrices);
                }
                if(! (entity instanceof PlayerEntity || entity instanceof ItemEntity || entity instanceof HostileEntity) && passive.getValue()){
                    RenderUtills.renderOutline(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(0, 255, 0, 153), matrices);
                }
            }else {
                if(entity instanceof PlayerEntity && player.getValue()){
                    RenderUtills.renderFilled(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(255, 0, 0, 153), matrices);
                }
                if(entity instanceof ItemEntity && item.getValue()){
                    RenderUtills.renderFilled(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(0, 255, 255, 153), matrices);
                }
                if(entity instanceof HostileEntity && hostile.getValue()){
                    RenderUtills.renderFilled(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(255, 255, 0, 153), matrices);
                }
                if(! (entity instanceof PlayerEntity || entity instanceof ItemEntity || entity instanceof HostileEntity) && passive.getValue()){
                    RenderUtills.renderFilled(entity.getPos().subtract(new Vec3d(entity.getWidth(), 0, entity.getWidth()).multiply(0.5)), new Vec3d(entity.getWidth(), entity.getHeight(), entity.getWidth()), new Color(0, 255, 0, 153), matrices);
                }
            }
        }
    }
}
