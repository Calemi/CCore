package com.calemi.ccore.api.client.screen;

public class ScreenRect {

    public final int width;
    public final int height;
    public int x;
    public int y;

    public ScreenRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(int x, int y) {
        return x > this.x - 1 && x < this.x + this.width + 1 && y > this.y - 1 && y < this.y + this.height + 1;
    }
}
