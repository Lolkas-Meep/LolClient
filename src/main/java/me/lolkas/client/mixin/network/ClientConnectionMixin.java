package me.lolkas.client.mixin.network;

import me.lolkas.client.event.Event;
import me.lolkas.client.event.PacketEvent;
import me.lolkas.client.event.PacketEvents;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void packetReceive(Packet<T> packet, PacketListener listener, CallbackInfo ci){
        if(Event.Packet.fireEvent(PacketEvents.PACKET_RECEIVE, new PacketEvent(packet))) ci.cancel();
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void send(Packet<?> packet, CallbackInfo ci){
        if(Event.Packet.fireEvent(PacketEvents.PACKET_SEND, new PacketEvent(packet))) ci.cancel();
    }
}
