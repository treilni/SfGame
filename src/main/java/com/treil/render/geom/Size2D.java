package com.treil.render.geom;

/**
 * @author Nicolas
 * @since 05/10/2017.
 */
public class Size2D {
    private int width;
    private int height;

    public Size2D(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
