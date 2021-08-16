package me.lolkas.client.utills;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtills {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static Vec3d getCameraPos()
    {
        return client.getBlockEntityRenderDispatcher().camera.getPos();
    }
    public static BlockPos getCameraBlockPos(){return client.getBlockEntityRenderDispatcher().camera.getBlockPos();}

    public static void applyRegionalRenderOffset(MatrixStack matrixStack)
    {
        Vec3d camPos = getCameraPos();
        BlockPos blockPos = getCameraBlockPos();

        int regionX = (blockPos.getX() >> 9) * 512;
        int regionZ = (blockPos.getZ() >> 9) * 512;

        matrixStack.translate(regionX - camPos.x, -camPos.y,
                regionZ - camPos.z);
    }
    public static void line(Vec3d start, Vec3d end, Color color, MatrixStack matrices) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        Camera c = client.gameRenderer.getCamera();
        Vec3d camPos = c.getPos();
        start = start.subtract(camPos);
        end = end.subtract(camPos);
        Matrix4f matrix = matrices.peek().getModel();
        float x1 = (float) start.x;
        float y1 = (float) start.y;
        float z1 = (float) start.z;
        float x2 = (float) end.x;
        float y2 = (float) end.y;
        float z2 = (float) end.z;
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();
        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION_COLOR);

        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();

        buffer.end();

        BufferRenderer.draw(buffer);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableBlend();
    }
    public static Vec3d getCrosshairVector() {

        Camera camera = client.gameRenderer.getCamera();

        ClientPlayerEntity player = client.player;

        float f = 0.017453292F;
        float pi = (float) Math.PI;

        assert player != null;
        float f1 = MathHelper.cos(-player.getYaw() * f - pi);
        float f2 = MathHelper.sin(-player.getYaw() * f - pi);
        float f3 = -MathHelper.cos(-player.getPitch() * f);
        float f4 = MathHelper.sin(-player.getPitch() * f);

        return new Vec3d(f2 * f3, f4, f1 * f3).add(camera.getPos());
    }

    public static void renderOutline(Vec3d start, Vec3d dimensions, Color color, MatrixStack stack) {
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();
        BufferBuilder buffer = renderPrepare(color);

        renderOutlineIntern(start, dimensions, stack, buffer);

        buffer.end();
        BufferRenderer.draw(buffer);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableBlend();
    }

    public static void renderOutlineIntern(Vec3d start, Vec3d dimensions, MatrixStack stack, BufferBuilder buffer) {
        Camera c = client.gameRenderer.getCamera();
        Vec3d camPos = c.getPos();
        start = start.subtract(camPos);
        Vec3d end = start.add(dimensions);
        Matrix4f matrix = stack.peek().getModel();
        float x1 = (float) start.x;
        float y1 = (float) start.y;
        float z1 = (float) start.z;
        float x2 = (float) end.x;
        float y2 = (float) end.y;
        float z2 = (float) end.z;

        buffer.vertex(matrix, x1, y1, z1).next();
        buffer.vertex(matrix, x1, y1, z2).next();
        buffer.vertex(matrix, x1, y1, z2).next();
        buffer.vertex(matrix, x2, y1, z2).next();
        buffer.vertex(matrix, x2, y1, z2).next();
        buffer.vertex(matrix, x2, y1, z1).next();
        buffer.vertex(matrix, x2, y1, z1).next();
        buffer.vertex(matrix, x1, y1, z1).next();

        buffer.vertex(matrix, x1, y2, z1).next();
        buffer.vertex(matrix, x1, y2, z2).next();
        buffer.vertex(matrix, x1, y2, z2).next();
        buffer.vertex(matrix, x2, y2, z2).next();
        buffer.vertex(matrix, x2, y2, z2).next();
        buffer.vertex(matrix, x2, y2, z1).next();
        buffer.vertex(matrix, x2, y2, z1).next();
        buffer.vertex(matrix, x1, y2, z1).next();

        buffer.vertex(matrix, x1, y1, z1).next();
        buffer.vertex(matrix, x1, y2, z1).next();

        buffer.vertex(matrix, x2, y1, z1).next();
        buffer.vertex(matrix, x2, y2, z1).next();

        buffer.vertex(matrix, x2, y1, z2).next();
        buffer.vertex(matrix, x2, y2, z2).next();

        buffer.vertex(matrix, x1, y1, z2).next();
        buffer.vertex(matrix, x1, y2, z2).next();
    }

    public static BufferBuilder renderPrepare(Color color) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        RenderSystem.setShaderColor(red, green, blue, alpha);
        RenderSystem.lineWidth(2f);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION);
        return buffer;
    }

    public static Color modify(Color original, int redOverwrite, int greenOverwrite, int blueOverwrite, int alphaOverwrite) {
        return new Color(redOverwrite == -1 ? original.getRed() : redOverwrite, greenOverwrite == -1 ? original.getGreen() : greenOverwrite, blueOverwrite == -1 ? original.getBlue() : blueOverwrite, alphaOverwrite == -1 ? original.getAlpha() : alphaOverwrite);
    }
    public static void fill(MatrixStack matrices, Color c, double x1, double y1, double x2, double y2) {
        int color = c.getRGB();
        double j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }
        Matrix4f matrix = matrices.peek().getModel();
        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float) x1, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, (float) x2, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void fill(Color c, double x1, double y1, double x2, double y2) {
        fill(new MatrixStack(), c, x1, y1, x2, y2);
    }
    public static void renderFilled(Vec3d start, Vec3d dimensions, Color color, MatrixStack stack) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        Camera c = client.gameRenderer.getCamera();
        Vec3d camPos = c.getPos();
        start = start.subtract(camPos);
        Vec3d end = start.add(dimensions);
        Matrix4f matrix = stack.peek().getModel();
        float x1 = (float) start.x;
        float y1 = (float) start.y;
        float z1 = (float) start.z;
        float x2 = (float) end.x;
        float y2 = (float) end.y;
        float z2 = (float) end.z;
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();
        buffer.begin(VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).next();

        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).next();

        buffer.end();

        BufferRenderer.draw(buffer);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableBlend();
    }
}
