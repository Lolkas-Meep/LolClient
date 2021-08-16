package me.lolkas.client.funStuff.modules.world;

import me.lolkas.client.event.Event;
import me.lolkas.client.event.PacketEvent;
import me.lolkas.client.event.PacketEvents;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import me.lolkas.client.utills.PlayerUtills;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class Freecam extends Module {
    final SliderValue HorizontalSpeed = (SliderValue) this.config.create("HorizontalSpeed", 1, 0, 10, 1);
    final SliderValue VerticalSpeed = (SliderValue) this.config.create("VerticalSpeed", 1,0,10,1);
    Vec3d pos;
    float yaw = 0f;
    float pitch = 0f;


    public Freecam(){
        super("Freecam", ModuleType.WORLD,"");
        Event.Packet.registerEventHandler(PacketEvents.PACKET_SEND, event1 -> {
            if(!this.isEnabled()) return;
            PacketEvent event = (PacketEvent) event1;
            if (event.getPacket() instanceof PlayerMoveC2SPacket) event.setCancelled(true);
            if (event.getPacket() instanceof PlayerInputC2SPacket) event.setCancelled(true);
        });
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        PlayerUtills.blockMovement();
        client.gameRenderer.setRenderHand(false);
        pos = client.player.getPos();
        pitch = client.player.getPitch();
        yaw = client.player.getYaw();
    }

    @Override
    public void disable() {
        PlayerUtills.unblockMovement();
        client.gameRenderer.setRenderHand(true);
        client.player.updatePosition(pos.x, pos.y, pos.z);
        client.player.setYaw(yaw);
        client.player.setPitch(pitch);
        client.player.noClip = false;
        client.getCameraEntity().noClip = false;
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {
        client.player.setSwimming(false);
        client.player.setPose(EntityPose.STANDING);
        client.player.noClip = true;
        client.getCameraEntity().noClip = true;
    }

    @Override
    public void onFastTick() {
        GameOptions go = client.options;
        float y = client.player.getYaw();
        int mx = 0, my = 0, mz = 0;

        if (go.keyJump.isPressed())
            my++;
        if (go.keyBack.isPressed())
            mz++;
        if (go.keyLeft.isPressed())
            mx--;
        if (go.keyRight.isPressed())
            mx++;
        if (go.keySneak.isPressed())
            my--;
        if (go.keyForward.isPressed())
            mz--;
        double ts = HorizontalSpeed.getValue() / 10;
        double vs = VerticalSpeed.getValue() / 10;
        double s = Math.sin(Math.toRadians(y));
        double c = Math.cos(Math.toRadians(y));
        double nx = ts * mz * s;
        double nz = ts * mz * -c;
        double ny = vs * my;
        nx += ts * mx * -c;
        nz += ts * mx * -s;
        Vec3d nv3 = new Vec3d(nx, ny, nz);
        Vec3d ppos = client.player.getPos().add(nv3);
        client.player.updatePosition(ppos.x, ppos.y, ppos.z);

        client.player.setVelocity(0, 0, 0);
    }
}
