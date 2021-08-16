package me.lolkas.client.funStuff.modules.render;

import me.lolkas.client.Client;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleType;
import me.lolkas.client.funStuff.modules.config.SliderValue;
import me.lolkas.client.utills.RenderUtills;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestESP extends Module {
    Thread thread;
    final SliderValue range = this.config.create("Range of search", 60, 30, 100, 0);
    List<RenderBlock> blocksToRender = new ArrayList<>();
    Map<Block, Color> renders = new HashMap<>();
    int t = 0;

    public ChestESP() {
        super("ChestESP", ModuleType.RENDER, "base hunting time");
        renders.put(Blocks.CHEST, new Color(255, 153, 0, 120));
        renders.put(Blocks.ENDER_CHEST, new Color(204, 51, 255, 120));
        renders.put(Blocks.HOPPER, new Color(47, 66, 62, 120));
        renders.put(Blocks.DROPPER, new Color(52, 52, 52, 120));
        renders.put(Blocks.DISPENSER, new Color(52, 52, 52, 120));
        Color shulker = new Color(232, 0, 255, 120);
        for (Block b : new Block[]{
                Blocks.SHULKER_BOX,
                Blocks.WHITE_SHULKER_BOX,
                Blocks.ORANGE_SHULKER_BOX,
                Blocks.MAGENTA_SHULKER_BOX,
                Blocks.LIGHT_BLUE_SHULKER_BOX,
                Blocks.YELLOW_SHULKER_BOX,
                Blocks.LIME_SHULKER_BOX,
                Blocks.PINK_SHULKER_BOX,
                Blocks.GRAY_SHULKER_BOX,
                Blocks.LIGHT_GRAY_SHULKER_BOX,
                Blocks.CYAN_SHULKER_BOX,
                Blocks.PURPLE_SHULKER_BOX,
                Blocks.BLUE_SHULKER_BOX,
                Blocks.BROWN_SHULKER_BOX,
                Blocks.GREEN_SHULKER_BOX,
                Blocks.RED_SHULKER_BOX,
                Blocks.BLACK_SHULKER_BOX
        }) renders.put(b, shulker);
        thread = new Thread(() -> {
            try {
                while (true){
                    Thread.sleep(50);
                    if (!this.isEnabled()) continue;
                    if (Client.client.player == null || Client.client.world == null) continue;
                    t++;
                    if (t > 8) {
                        scan();
                        t = 0;
                    }
                }
            } catch (Exception e){

            }
        });
        thread.start();
    }

    void scan() {
        List<RenderBlock> cache = new ArrayList<>();
        double rangeMid = range.getValue() / 2;
        for (double y = -rangeMid; y < rangeMid; y++) {
            for (double x = -rangeMid; x < rangeMid; x++) {
                for (double z = -rangeMid; z < rangeMid; z++) {
                    Vec3d pos = new Vec3d(x, y, z);
                    BlockPos bp = new BlockPos(pos);
                    BlockPos bp1 = Client.client.player.getBlockPos().add(bp);
                    if (bp1.getY() < 0 || bp1.getY() > 255) break;
                    BlockState state = Client.client.world.getBlockState(bp1);
                    if (getBlock(state.getBlock()) != null) {
                        cache.add(new RenderBlock(bp1, state.getBlock()));
                    }
                }
            }
        }
        blocksToRender.clear();
        blocksToRender.addAll(cache);
        cache.clear();
    }

    Color getBlock(Block b) {
        return renders.get(b);
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
        if(!this.isEnabled()) return;
        if(Client.client.world == null || Client.client.player == null) return;
        for (RenderBlock blockPos : blocksToRender.toArray(new RenderBlock[0])) {
            Vec3d v = new Vec3d(blockPos.bp.getX(), blockPos.bp.getY(), blockPos.bp.getZ());
            Color c = getBlock(blockPos.b);
            RenderUtills.renderFilled(v, new Vec3d(1, 1, 1), c, matrices);
        }
    }

    record RenderBlock(BlockPos bp, Block b) {

    }
}
