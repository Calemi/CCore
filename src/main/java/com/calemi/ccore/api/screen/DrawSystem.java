package com.calemi.ccore.api.screen;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DrawSystem {

    private final TextRenderer textRenderer;

    private final int screenX;
    private final int screenY;

    public DrawSystem(TextRenderer textRenderer, int screenX, int screenY) {
        this.textRenderer = textRenderer;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void drawItemWithTooltip(ItemStack stack, int x, int y, int mouseX, int mouseY, DrawContext context) {

        context.drawItem(stack, x, y);

        if (!stack.isEmpty() && new ScreenRect(screenX + x, screenY + y, 16, 16).contains(mouseX, mouseY)) {
            context.drawItemTooltip(textRenderer, stack, mouseX - screenX, mouseY - screenY);
        }
    }

    public void drawHoveringTooltip(Text text, boolean shadow, ScreenRect hoverBounds, int mouseX, int mouseY, DrawContext context) {
        drawHoveringTooltip(new Text[]{text}, shadow, hoverBounds, mouseX, mouseY, context);
    }

    public void drawHoveringTooltip(Text[] text, boolean shadow, ScreenRect hoverBounds, int mouseX, int mouseY, DrawContext context) {

        hoverBounds.x += screenX;
        hoverBounds.y += screenY;

        if (hoverBounds.contains(mouseX, mouseY)) {

            drawTooltip(text, shadow, false, mouseX + 8, mouseY - 13, context);
        }
    }

    public void drawTooltip(Text text, boolean shadow, boolean centeredString, int x, int y, DrawContext context) {
        drawTooltip(new Text[]{text}, shadow, centeredString, x, y, context);
    }

    public void drawTooltip(Text[] text, boolean shadow, boolean centeredString, int x, int y, DrawContext context) {

        int maxLength = 0;

        for (Text line : text) {

            if (textRenderer.getWidth(line) > maxLength) {
                maxLength = textRenderer.getWidth(line) ;
            }
        }

        int boxX = x + 3 + (centeredString ? - maxLength / 2 : 0);
        int boxY = y + 3;
        int boxWidth = maxLength;
        int boxHeight = 7 + ((text.length - 1) * 9);

        context.getMatrices().push();

        context.getMatrices().translate(0, 0, 1000);

        TooltipBackgroundRenderer.render(context, boxX - screenX, boxY - screenY, boxWidth, boxHeight, -100);

        for (int i = 0; i < text.length; i++) {

            int textX = x + 3 + (centeredString ? -(int) (textRenderer.getWidth(text[i]) / 2) : 0);
            int textY = y + 3 + (i * 9);

            context.drawText(textRenderer, text[i], textX - screenX, textY - screenY, Formatting.WHITE.getColorValue(), shadow);
        }

        context.getMatrices().pop();
    }

    public void drawCenteredText(MutableText text, boolean shadow, int x, int y, DrawContext context) {
        context.drawText(textRenderer, text.setStyle(Style.EMPTY.withColor(4210752)), x - textRenderer.getWidth(text) / 2, y, Formatting.WHITE.getColorValue(), shadow);
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
