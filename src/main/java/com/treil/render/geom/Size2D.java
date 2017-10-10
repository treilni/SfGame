package com.treil.render.geom;

/**
 * Immutable size object
 *
 * @author Nicolas
 * @since 05/10/2017.
 */
public class Size2D implements Cloneable {
    private int width;
    private int height;

    public Size2D(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
