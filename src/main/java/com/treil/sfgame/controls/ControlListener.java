package com.treil.sfgame.controls;

/**
 * @author Nicolas
 * @since 16/10/2017.
 */
public interface ControlListener {
    boolean isActive();

    void processAction(Action action);
}
