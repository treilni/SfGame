package com.treil.sfgame.messaging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Nicolas
 * @since 14/12/2017.
 */
class XEventBusTest {
    class Listener<E extends Event> implements EventListener<E> {
        int eventsReceived = 0;

        @Override
        public void onEvent(E event) {
            eventsReceived++;
        }
    }

    class ComplexEvent extends Event {

    }

    class ComplexEvent2 extends Event {

    }

    @Test
    void fireEvent() {
        Listener<Event> listener = new Listener<>();

        XEventBus.registerListener(listener, Event.class);
        Assertions.assertTrue(listener.eventsReceived == 0);
        XEventBus.fireEvent(new Event());
        Assertions.assertTrue(listener.eventsReceived == 1);
    }

    @Test
    void listenComplexEvent() {
        Listener<ComplexEvent> listener = new Listener<>();
        XEventBus.registerListener(listener, ComplexEvent.class);
        XEventBus.fireEvent(new Event());
        Assertions.assertTrue(listener.eventsReceived == 0);
    }

    @Test
    void fireComplexEvent() {
        Listener<Event> listener = new Listener<>();
        Listener<ComplexEvent> listener2 = new Listener<>();
        XEventBus.registerListener(listener, Event.class);
        XEventBus.registerListener(listener2, ComplexEvent.class);
        XEventBus.fireEvent(new Event());
        Assertions.assertTrue(listener.eventsReceived == 1);
        Assertions.assertTrue(listener2.eventsReceived == 0);
    }

    @Test
    void fireVeryComplexEvent() {
        Listener<Event> listenerX = new Listener<>();
        Listener<ComplexEvent> listener = new Listener<>();
        Listener<ComplexEvent2> listener2 = new Listener<>();
        XEventBus.registerListener(listenerX, Event.class);
        XEventBus.registerListener(listener, ComplexEvent.class);
        XEventBus.registerListener(listener2, ComplexEvent2.class);
        XEventBus.fireEvent(new ComplexEvent());
        XEventBus.fireEvent(new ComplexEvent2());
        Assertions.assertTrue(listenerX.eventsReceived == 2);
        Assertions.assertTrue(listener.eventsReceived == 1);
        Assertions.assertTrue(listener2.eventsReceived == 1);
    }
}