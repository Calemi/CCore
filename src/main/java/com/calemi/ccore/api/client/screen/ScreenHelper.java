package com.calemi.ccore.api.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ScreenHelper {

    private static final Minecraft MC = Minecraft.getInstance();

    public static void drawTooltipHoverRect(GuiGraphics graphics, ScreenRectangle hoverRect, int mouseX, int mouseY, Component... messages) {

        if (hoverRect.containsPoint(mouseX, mouseY)) {
            graphics.renderComponentTooltip(MC.font, List.of(messages), mouseX, mouseY);
        }
    }
}
