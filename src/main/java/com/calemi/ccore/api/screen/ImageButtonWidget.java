package com.calemi.ccore.api.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ImageButtonWidget extends PressableWidget {

    protected final PressAction onPress;

    protected final int u;
    protected final int v;
    protected final int vHoverOffset;

    protected final Identifier texture;

    public ImageButtonWidget(PressAction onPress, int x, int y, int u, int v, int vHoverOffset, Identifier texture) {
        this(onPress, x, y, u, v, vHoverOffset, 19, 19, texture);
    }

    public ImageButtonWidget(PressAction onPress, int x, int y, int u, int v, int vHoverOffset, int width, int height, Identifier texture) {
        super(x, y, width, height, Text.empty());
        this.onPress = onPress;
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.vHoverOffset = vHoverOffset;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        context.drawTexture(texture, getX(), getY(), u, v + (isSelected() ? vHoverOffset : 0), getWidth(), getHeight());

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }

    @Environment(EnvType.CLIENT)
    public interface PressAction {
        void onPress(ImageButtonWidget button);
    }
}
