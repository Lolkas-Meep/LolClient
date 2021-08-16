package me.lolkas.client.funStuff.modules.world;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Xray extends Module {
    final public static List<Block> blocks = new ArrayList<>();


    public Xray(){
        super("Xray", ModuleType.WORLD, "some off camera mining");
        Registry.BLOCK.forEach(block -> {
            if(renderBlock(block)) blocks.add(block);
        });
    }

    boolean renderBlock(Block block){
        boolean c1 = block == Blocks.CHEST || block == Blocks.FURNACE || block == Blocks.ENDER_CHEST || block == Blocks.SMOKER || block == Blocks.BLAST_FURNACE || block == Blocks.ANCIENT_DEBRIS;
        boolean c2 = block instanceof OreBlock || block instanceof RedstoneOreBlock;
        return  c1 || c2;
    }

    @Override
    public void tick() {

    }

    @Override
    public void enable() {
        client.worldRenderer.reload();
    }

    @Override
    public void disable() {
        client.worldRenderer.reload();
    }

    @Override
    public void onWorldRender(MatrixStack matrices, float tickDelta) {

    }
}
