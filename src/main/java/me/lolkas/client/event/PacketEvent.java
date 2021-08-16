package me.lolkas.client.event;

import net.minecraft.network.Packet;

public class PacketEvent {
    private final Packet<?> packet;
    private boolean cancelled = false;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }
    public boolean isCancelled(){return cancelled;}
    public void setCancelled(boolean cancelled){this.cancelled = cancelled;}
    public Packet<?> getPacket(){return packet;}
}
