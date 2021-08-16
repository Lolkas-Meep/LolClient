package me.lolkas.client.utills;

import me.lolkas.client.mixin.player.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerUtills {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Input INPUT_BLOCK = new Input(){
        @Override
        public void tick(boolean slowDown) {
            this.movementForward = 0f;
            this.movementSideways = 0f;
        }
    };
    private static Input INPUT_NORMAL = null;

    public static float getHealthTotal(PlayerEntity target){
        return target.getHealth() + target.getAbsorptionAmount();
    }
    static public void leftClick(){
        MinecraftClient.getInstance().options.keyAttack.setPressed(true);
        ((IMinecraftClient) MinecraftClient.getInstance()).leftClick();
        MinecraftClient.getInstance().options.keyAttack.setPressed(false);
    }
    static public void rightClick(){
        ((IMinecraftClient) MinecraftClient.getInstance()).rightClick();
    }

    public static void blockMovement(){
        INPUT_NORMAL = client.player.input;
        client.player.input = INPUT_BLOCK;
    }
    public static void unblockMovement(){
        if(INPUT_NORMAL != null) client.player.input = INPUT_NORMAL;
    }
}
