package com.lolkas.client.mixin.player;

import com.lolkas.client.Client;
import com.lolkas.client.funStuff.Module;
import com.lolkas.client.funStuff.ModuleRegistry;
import com.lolkas.client.utills.ChatUtills;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
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
            if(Client.client.player.getInventory().getMainHandStack()
                    .getItem() != Items.WRITABLE_BOOK)
            {
                ChatUtills.sendPlayerMessage("You must hold a book & quill in your main hand.");
                ci.cancel();
                return;
            }


            NbtList listTag = new NbtList();

            StringBuilder builder1 = new StringBuilder();
            for(int i = 0; i < 21845; i++)
                builder1.append((char)2077);

            listTag.addElement(0, NbtString.of(builder1.toString()));

            StringBuilder builder2 = new StringBuilder();
            for(int i = 0; i < 32; i++)
                builder2.append("AMOGUS!!!");

            String string2 = builder2.toString();
            for(int i = 1; i < 40; i++)
                listTag.addElement(i, NbtString.of(string2));

            ItemStack bookStack = new ItemStack(Items.WRITABLE_BOOK, 1);
            bookStack.putSubTag("title",
                    NbtString.of("If you can see this, it didn't work"));
            bookStack.putSubTag("pages", listTag);

            Client.client.player.networkHandler.sendPacket(new BookUpdateC2SPacket(bookStack,
                    true, Client.client.player.getInventory().selectedSlot));
            ci.cancel();
        }
    }
}
