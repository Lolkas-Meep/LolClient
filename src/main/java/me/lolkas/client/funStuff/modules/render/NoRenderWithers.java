package me.lolkas.client.funStuff.modules.render;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;

public class NoRenderWithers extends Module {

    public boolean noRenderEntity(Entity entity){
        return !this.isEnabled() && (entity instanceof WitherSkullEntity || entity instanceof WitherEntity);
    }

    public NoRenderWithers() {
        super("NoRenderWithers", ModuleType.RENDER, "wither");
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

    }

}
