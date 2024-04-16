package com.calemi.ccore.api.screen;

import com.calemi.ccore.api.general.CCoreMathHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ScrollableLongField {

    private final DrawSystem drawSystem;

    private long value;
    private final long minValue;
    private final long maxValue;
    private int power;

    private int x;
    private int y;

    private MutableText text;
    private MutableText[] tooltip;

    private final Runnable onScroll;

    public ScrollableLongField(long value, long minValue, long maxValue, int x, int y, DrawSystem drawSystem, Runnable onScroll) {
        this.drawSystem = drawSystem;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.x = x;
        this.y = y;
        this.onScroll = onScroll;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ScreenRect getRect() {
        return new ScreenRect(x - (56 / 2), y - 3, 56, 14);
    }

    public long getDelta() {
        return (long)Math.pow(10, power);
    }

    public void setText(MutableText text) {
        this.text = text;
    }

    public void setTooltip(MutableText... tooltip) {
        this.tooltip = tooltip;
    }

    public void mouseScrolled(double mouseX, double mouseY, double amount) {

        ScreenRect hoverRect = getRect();
        hoverRect.x += drawSystem.getScreenX();
        hoverRect.y += drawSystem.getScreenY();

        if (hoverRect.contains((int)mouseX, (int)mouseY)) {

            if (Screen.hasShiftDown()) {
                power += (int)amount;
                power = MathHelper.clamp(power, 0, 15);
            }

            else {
                value += getDelta() * (int)amount;
                value = CCoreMathHelper.clamp(value, minValue, maxValue);
                onScroll.run();
            }
        }
    }

    public void render(int mouseX, int mouseY, DrawContext context) {

        if (text != null) {
            drawSystem.drawCenteredText(text, false, x, y, context);
        }

        if (tooltip != null) drawSystem.drawHoveringTooltip(tooltip, true, getRect(), mouseX, mouseY, context);
    }
}
