package com.calemi.ccore.api.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ToggleImageButtonWidget extends ImageButtonWidget {

    private final int uToggleOffset;
    private boolean isToggled;
    private Tooltip toggledTooltip;

    public ToggleImageButtonWidget(PressAction onPress, int x, int y, int u, int v, int uToggleOffset, int vHoverOffset, boolean isToggled, Identifier texture) {
        super(onPress, x, y, u, v, vHoverOffset, texture);
        this.uToggleOffset = uToggleOffset;
        this.isToggled = isToggled;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        context.drawTexture(texture, getX(), getY(), u + (isToggled ? uToggleOffset : 0), v + (isSelected() ? vHoverOffset : 0), getWidth(), getHeight());

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void onPress() {
        super.onPress();
        isToggled = !isToggled;
    }

    public boolean isToggled() {
        return isToggled;
    }
}
