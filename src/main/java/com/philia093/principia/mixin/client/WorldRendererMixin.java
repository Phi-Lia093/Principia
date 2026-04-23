package com.philia093.principia.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.philia093.principia.Principia;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow private ClientWorld world;
    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta,
                             Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        if (world != null && world.getRegistryKey().getValue().toString().equals(Principia.MOD_ID + ":space")) {
            renderTestSquareOnSkySphere(matrices, camera, tickDelta);
            ci.cancel();
        }
    }

    @Unique
    private void renderTestSquareOnSkySphere(MatrixStack matrices, Camera camera, float tickDelta) {
        matrices.push();

        matrices.loadIdentity();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        float distance = 100.0f;
        float squareSize = 20.0f;

        float x = 0;
        float y = 0;
        float z = distance;

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        buffer.vertex(matrix, x - squareSize/2, y - squareSize/2, z).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        buffer.vertex(matrix, x + squareSize/2, y - squareSize/2, z).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        buffer.vertex(matrix, x + squareSize/2, y + squareSize/2, z).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        buffer.vertex(matrix, x - squareSize/2, y + squareSize/2, z).color(1.0f, 1.0f, 1.0f, 1.0f).next();

        tessellator.draw();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

        matrices.pop();
    }
}
