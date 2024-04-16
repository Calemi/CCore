package com.calemi.ccore.api.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ScreenErrorSystem {

    private final int x;
    private final int y;

    private float showTicks;

    private Text currentText;

    public ScreenErrorSystem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void showError(Text text) {
        showError(text, 3);
    }

    public void showError(Text text, float seconds) {
        currentText = text;
        showTicks = seconds * 20;
    }

    public void tick() {

        if (showTicks > 0) {
            showTicks--;
        }

        else {
            currentText = Text.empty();
        }
    }

    public void render(DrawSystem drawSystem, DrawContext context) {

        if (showTicks > 0) {
            drawSystem.drawTooltip(currentText.copy().formatted(Formatting.RED), true, true, x, y, context);
        }
    }
}
