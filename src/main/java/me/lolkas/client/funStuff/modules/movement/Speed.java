package me.lolkas.client.funStuff.modules.movement;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class Speed extends Module {
    final SliderValue sliderSpeed = this.config.create("Max Speed", 0.66, 0.5, 2, 2);
    public Speed(){
        super("Speed", ModuleType.MOVEMENT, "gota go fast");
    }

    @Override
    public void tick() {
        if (client.player.isSneaking() || client.player.forwardSpeed == 0 && client.player.sidewaysSpeed == 0)
            return;
        if (client.player.forwardSpeed > 0 && !client.player.horizontalCollision)
            client.player.setSprinting(true);
        if (!client.player.isOnGround())
            return;

        Vec3d vel = client.player.getVelocity();
        client.player.setVelocity(vel.x * 1.8, vel.y + 0.1, vel.z * 1.8);
        vel = client.player.getVelocity();
        double currentSpeed = Math.sqrt(Math.pow(vel.x, 2) + Math.pow(vel.z, 2));

        double maxSpeed = sliderSpeed.getValue();

        if (currentSpeed > maxSpeed)
            client.player.setVelocity(vel.x / currentSpeed * maxSpeed, vel.y,vel.z / currentSpeed * maxSpeed);
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
        client.player.setSprinting(false);
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {

    }
}
