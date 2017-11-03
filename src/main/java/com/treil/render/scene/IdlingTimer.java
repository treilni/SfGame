package com.treil.render.scene;

/**
 * @author Nicolas
 * @since 31/10/2017.
 */
public class IdlingTimer {
    private long delayMs;
    private long nextElapsed = 0;

    public IdlingTimer(long delayMs) {
        this.delayMs = delayMs;
    }

    public IdlingTimer() {
        this(100L);
    }

    public boolean elapsed() {
        final long millis = System.currentTimeMillis();
        if (millis > nextElapsed) {
            nextElapsed = millis + delayMs;
            return true;
        }
        return false;
    }
}
