package me.lolkas.client.mixin.player;

import me.lolkas.client.Client;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.world.Freecam;
import me.lolkas.client.helper.config.ConfigModule;
import me.lolkas.client.utills.ChatUtills;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if(!ConfigModule.enabled) ConfigModule.enableModules();
        for(Module module : ModuleRegistry.getModules()){
            if(module.isEnabled()) module.tick();
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"sendChatMessage"},
            cancellable = true
    )
    public void onChatMessage(String message, CallbackInfo ci){
        boolean dupe = message.equals(".d");
        if(dupe){
            if(Client.client.player.getInventory().getMainHandStack().getItem() != Items.WRITABLE_BOOK)
            {
                ChatUtills.sendPlayerMessage("You must hold a book & quill in your main hand.");
                ci.cancel();
                return;
            }
            BookUpdateC2SPacket b = new BookUpdateC2SPacket(client.player.getInventory().selectedSlot, new ArrayList<>(), java.util.Optional.of("\0".repeat(65500)));
            ci.cancel();
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double z, CallbackInfo ci){
        if(ModuleRegistry.getByClass(Freecam.class).isEnabled()) ci.cancel();
    }
}
