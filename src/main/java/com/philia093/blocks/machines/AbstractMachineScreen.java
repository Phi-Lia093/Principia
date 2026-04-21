package com.philia093.blocks.machines;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class AbstractMachineScreen<T extends AbstractMachineScreenHandler> extends HandledScreen<T> {
    protected final Identifier texture;
    protected int progressBarX;
    protected int progressBarY;
    protected int progressBarWidth;
    protected int progressBarHeight;
    protected int textureU;
    protected int textureV;

    protected AbstractMachineScreen(T handler, PlayerInventory inventory, Text title, Identifier texture) {
        super(handler, inventory, title);
        this.texture = texture;
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
        this.playerInventoryTitleY = this.backgroundHeight - 94;

        // 默认进度条位置
        this.progressBarX = 79;
        this.progressBarY = 35;
        this.progressBarWidth = 24;
        this.progressBarHeight = 17;
        this.textureU = 176;
        this.textureV = 0;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        // 绘制背景
        context.drawTexture(texture, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // 绘制进度条
        drawProgressBar(context, x, y);

        // 子类可添加额外的渲染
        drawExtraElements(context, x, y);
    }

    protected void drawProgressBar(DrawContext context, int x, int y) {
        int progress = handler.getProgress();
        int maxProgress = handler.getMaxProgress();

        if (maxProgress > 0 && progress > 0) {
            int width = (int) (progressBarWidth * ((float) progress / maxProgress));
            context.drawTexture(texture, x + progressBarX, y + progressBarY,
                    textureU, textureV, width, progressBarHeight);
        }
    }

    protected void drawExtraElements(DrawContext context, int x, int y) {
        // 子类可重写此方法添加额外渲染
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}