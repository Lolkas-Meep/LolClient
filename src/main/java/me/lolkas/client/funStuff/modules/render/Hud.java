package me.lolkas.client.funStuff.modules.render;

import me.lolkas.client.Client;
import me.lolkas.client.event.Event;
import me.lolkas.client.event.PacketEvents;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.mixin.player.IMinecraftClient;
import me.lolkas.client.utills.MiscUtills;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Hud extends Module {
    DateFormat df = new SimpleDateFormat("h:mm aa");
    long worldUpPac;
    double tps;

    public Hud(){
        super("Hud", ModuleType.RENDER, "turn on to gaming");
        worldUpPac = System.currentTimeMillis();

        Event.Packet.registerEventHandler(PacketEvents.PACKET_RECEIVE, event -> {
            if(event.getPacket() instanceof WorldTimeUpdateS2CPacket){
                tps = MiscUtills.roundToN(notStolenFromAtomic(System.currentTimeMillis() - worldUpPac), 2);
                worldUpPac = System.currentTimeMillis();
            }
        });
    }
    double notStolenFromAtomic(double n)  {
        return (20.0 / Math.max((n - 1000.0) / (500.0), 1.0));
    }
    public void onHudRender(){
        if(this.isEnabled()){
            if(client.player == null || client.world == null || client.getNetworkHandler() == null) return;
            MatrixStack matrixStack = new MatrixStack();
            List<HudEntry> hudEntries = new ArrayList<>();
            //ip
            if(client.isInSingleplayer()){
                hudEntries.add(new HudEntry("IP", "Singleplayer", false));
            }
            if(client.getCurrentServerEntry() != null){
                hudEntries.add(new HudEntry("IP", client.getCurrentServerEntry().address, false));
            }
            //coords
            BlockPos playerBlockPos = client.player.getBlockPos();
            hudEntries.add(new HudEntry("XYZ", playerBlockPos.getX() + " " + playerBlockPos.getY() + " " + playerBlockPos.getZ(), false));
            //ping
            PlayerListEntry playerListEntry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            hudEntries.add(new HudEntry("Ping", (playerListEntry == null ? "?" : playerListEntry.getLatency() + "ms"), false));
            //Speed
            double px = client.player.prevX;
            double py = client.player.prevY;
            double pz = client.player.prevZ;
            double dist = new Vec3d(px, py, pz).distanceTo(client.player.getPos());
            hudEntries.add(new HudEntry("Speed", MiscUtills.roundToN(dist * 20, 2) + "", false));
            //Time
            hudEntries.add(new HudEntry("Time", df.format(new Date()), true));
            //Fps
            hudEntries.add(new HudEntry("Fps", ((IMinecraftClient) client).getFps() + "", false));
            //Tps
            hudEntries.add(new HudEntry("Tps", tps + "", false));
            int yOffset = 10;
            int xOffset = 5;
            int yROffset = 10;
            for (HudEntry hudEntry : hudEntries){
                String tv = hudEntry.text + " " + hudEntry.value;
                int yL = (hudEntry.renderRight) ? (client.getWindow().getScaledHeight() - yROffset) : (client.getWindow().getScaledHeight() - yOffset);
                float xL = (hudEntry.renderRight) ? (client.getWindow().getScaledWidth() - xOffset - Client.fontRenderer.getStringWidth(tv)) : xOffset;
                Color rgb = MiscUtills.getCurrentRGB();
                Client.fontRenderer.drawString(matrixStack, hudEntry.text + " " + hudEntry.value, xL, yL, rgb.getRGB());
                if(!hudEntry.renderRight){
                    yOffset += 10;
                }
            }

            Module[] modules = ModuleRegistry.getModules().stream()
                    .filter(Module::isEnabled)
                    .sorted(Comparator.comparingDouble(value -> Client.fontRenderer.getStringWidth(value.getName())))
                    .toArray(Module[]::new);
            ArrayUtils.reverse(modules);
            int moduleY = 10;
            float rgbIncrementer = 0.03f;
            float currentRgbSeed = (System.currentTimeMillis() % 4500) / 4500f;
            for (Module module : modules){
                currentRgbSeed %= 1f;
                int r = Color.HSBtoRGB(currentRgbSeed, 0.7f, 1f);
                currentRgbSeed += rgbIncrementer;
                float x = client.getWindow().getScaledWidth() - Client.fontRenderer.getStringWidth(module.getName()) - 3;
                Client.fontRenderer.drawString(matrixStack, module.getName(), x, moduleY + 1, r);
                moduleY += 10;
            }
            float tslt = (System.currentTimeMillis() - worldUpPac) / 1000f;
            if(tslt > 1){
                Color color;
                if(tslt > 10) color = Color.RED;
                else if(tslt > 3) color = Color.ORANGE;
                else color = Color.YELLOW;

                String string = "Time since last tick " + tslt;

                int y = client.getWindow().getScaledHeight() / 4;
                int x = (int) (client.getWindow().getScaledWidth() / 2 - Client.fontRenderer.getStringWidth(string) / 2);
                Client.fontRenderer.drawString(matrixStack, string, x,y, color.getRGB());
            }
        }
    }

    static class HudEntry {
        public String text;
        public String value;
        public boolean renderRight;

        public HudEntry(String text, String value, boolean renderRight){
            this.text = text;
            this.value = value;
            this.renderRight = renderRight;
        }
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
