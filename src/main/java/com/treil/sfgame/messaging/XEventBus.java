package com.treil.sfgame.messaging;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicolas
 * @since 14/12/2017.
 */
public class XEventBus {
    private static final XEventBus instance = new XEventBus();

    @Nonnull
    private final EventBus eventBus = new EventBus();

    @Nonnull
    private final Map<Class, List<EventListener>> listenerMap = new HashMap<>();

    public static <E extends Event> void fireEvent(@Nonnull E event) {
        instance.post(event);
    }

    public static <E extends Event> void registerListener(@Nonnull EventListener<E> listener, @Nonnull Class<E> clazz) {
        instance.register(clazz, listener);
    }

    private XEventBus() {
        eventBus.register(new Object() {
            @SuppressWarnings("unused")
            @Subscribe
            void dispatch(Event e) {
                if (e != null) {
                    final Class clazz = e.getClass();
                    listenerMap.forEach((c, listeners) -> {
                        //noinspection unchecked
                        if (c.isAssignableFrom(clazz)) {
                            if (listeners != null) {
                                listeners.forEach(eventListener -> {
                                    //noinspection unchecked
                                    eventListener.onEvent(e);
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private <E extends Event> void register(@Nonnull Class<E> clazz, @Nonnull EventListener<E> listener) {
        List<EventListener> eventListeners = listenerMap.computeIfAbsent(clazz, k -> new ArrayList<>());
        eventListeners.add(listener);
    }

    private <E extends Event> void post(E event) {
        eventBus.post(event);
    }
}
