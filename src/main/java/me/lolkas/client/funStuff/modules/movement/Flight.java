package me.lolkas.client.funStuff.modules.movement;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;


public class Flight extends Module {
    double speed;
    final SliderValue sliderSpeed = this.config.create("Speed", 1.0, 0.5, 5, 2);

    public Flight(){
        super("Flight", ModuleType.MOVEMENT, "yes");
    }


    @Override
    public void tick() {
        if(this.isEnabled()){
            speed = sliderSpeed.getValue();
            ClientPlayerEntity player = client.player;

            player.getAbilities().flying = false;
            player.flyingSpeed = Float.parseFloat(sliderSpeed.getValue().toString());

            player.setVelocity(0, 0, 0);
            Vec3d velocity = player.getVelocity();

            if(client.options.keyJump.isPressed())
                player.setVelocity(velocity.add(0, speed, 0));

            if(client.options.keySneak.isPressed())
                player.setVelocity(velocity.subtract(0, speed, 0));
        }
    }
    @Override
    public void enable(){

    }

    @Override
    public void disable() {

    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {

    }
}
