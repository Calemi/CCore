package com.calemi.ccore.api.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Simple object to store a Rectangle. Used
 */
@Environment(EnvType.CLIENT)
public class ScreenRect {

    public final int width;
    public final int height;
    public int x;
    public int y;

    /**
     * Creates a Screen Rectangle
     * @param x      The x coordinate of the Rectangle.
     * @param y      The y coordinate of the Rectangle.
     * @param width  The width of the Rectangle.
     * @param height The height of the Rectangle.
     */
    public ScreenRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @return True, if the given x and y are in the bounds of the Rectangle.
     */
    public boolean contains(int px, int py) {
        return px > x - 1 && px < (x + width) + 1 && py > y - 1 && py < (y + height) + 1;
    }
}
