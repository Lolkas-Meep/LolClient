package me.lolkas.client.mixin.world;

import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.world.Xray;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminace(CallbackInfoReturnable<Integer> cir){
        if(ModuleRegistry.getByClass(Xray.class).isEnabled()) cir.setReturnValue(15);
    }
}
