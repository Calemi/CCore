package com.calemi.ccore.api.screen;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;

public class ScreenHelper {

    public static void drawCenteredText(int x, int y, int hexColor, boolean shadow, MutableText text, DrawContext context, TextRenderer textRenderer) {
        context.drawText(textRenderer, text, x - textRenderer.getWidth(text) / 2, y, hexColor, shadow);
    }
}
