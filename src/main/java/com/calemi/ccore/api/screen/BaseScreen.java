package com.calemi.ccore.api.screen;

import com.calemi.ccore.api.screen.handler.BaseScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class BaseScreen<T extends BaseScreenHandler> extends HandledScreen<T> {

    public DrawSystem drawSystem;
    public ScreenErrorSystem errorSystem;

    public BaseScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    public abstract Identifier getTexture();

    @Override
    protected void init() {
        super.init();
        drawSystem = new DrawSystem(textRenderer, getScreenX(), getScreenY());
        errorSystem = new ScreenErrorSystem(getScreenX() + (backgroundWidth / 2), getScreenY() - 15);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        errorSystem.tick();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(getTexture(), getScreenX(), getScreenY(), 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        errorSystem.render(drawSystem, context);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public int getScreenX() {
        return (this.width - this.backgroundWidth) / 2;
    }

    public int getScreenY() {
        return (this.height - this.backgroundHeight) / 2;
    }

    public ClientPlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }
}