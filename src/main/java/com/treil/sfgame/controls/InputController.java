package com.treil.sfgame.controls;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nicolas
 * @since 27/09/2017.
 */
public class InputController {
    // Actions
    enum Action {
        LEFT, RIGHT, UP, DOWN, FORWARD, BACKWARD,
        NONE;

        private static Map<String, Action> map = null;

        @Nonnull
        static Action forName(@Nullable String name) {
            if (map == null) {
                map = new HashMap<>();
                for (Action action : values()) {
                    map.put(action.name(), action);
                }
            }
            final Action action = map.get(name);
            return action != null ? action : NONE;
        }

        @Nonnull
        public static String[] names() {
            return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList()).toArray(new String[0]);
        }
    }

    private InputManager inputManager;
    private CamMovementController camMovementController;

    public InputController(InputManager inputManager,
                           CamMovementController camMovementController) {
        this.inputManager = inputManager;

        // init keys
        addKeyMapping(Action.LEFT, KeyInput.KEY_LEFT);
        addKeyMapping(Action.RIGHT, KeyInput.KEY_RIGHT);
        addKeyMapping(Action.FORWARD, KeyInput.KEY_UP);
        addKeyMapping(Action.BACKWARD, KeyInput.KEY_DOWN);

        AnalogListener analogListener = (name, value, tpf) -> {
            switch (Action.forName(name)) {
                case LEFT:
                    camMovementController.moveLeft(value, tpf);
                    break;
                case RIGHT:
                    camMovementController.moveRight(value, tpf);
                    break;
                case UP:
                    break;
                case DOWN:
                    break;
                case FORWARD:
                    camMovementController.moveForward(value, tpf);
                    break;
                case BACKWARD:
                    camMovementController.moveBackward(value, tpf);
                    break;
                case NONE:
                    break;
            }
        };

        inputManager.addListener(analogListener, Action.names());
    }

    private void addKeyMapping(Action action, int key) {
        inputManager.addMapping(action.name(), new KeyTrigger(key));
    }
}
