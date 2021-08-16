package me.lolkas.client.funStuff.modules.movement;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public NoFall(){
        super("NoFall", ModuleType.MOVEMENT, "use with flight");
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

    @Override
    public void tick(){
        ClientPlayerEntity player = client.player;
        if(player.fallDistance <= (player.isFallFlying() ? 1 : 2))
            return;
        if(!(player.getVelocity().y < -0.5)){
            return;
        }
        player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
