package com.treil.sfgame.messaging;

/**
 * @author Nicolas
 * @since 14/12/2017.
 */
public interface EventListener<E extends Event> {
    void onEvent(E event);
}
