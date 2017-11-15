package com.treil.sfgame.controls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nicolas
 * @since 16/10/2017.
 */ // Actions
public enum Action {
    LEFT, RIGHT, UP, DOWN, FORWARD, BACKWARD,
    LEFT_CLICK, RIGHT_CLICK,
    CYCLE,
    NONE;

    private static Map<String, Action> map = null;

    @Nonnull
    static Action forName(@Nullable String name) {
        if (map == null) {
            final HashMap<String, Action> m = new HashMap<>();
            for (Action action : values()) {
                m.put(action.name(), action);
            }
            map = m;
        }
        final Action action = map.get(name);
        return action != null ? action : NONE;
    }

    @Nonnull
    public static String[] names() {
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList()).toArray(new String[0]);
    }
}
