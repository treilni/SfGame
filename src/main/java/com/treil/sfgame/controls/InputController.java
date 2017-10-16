package com.treil.sfgame.controls;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

import static com.treil.sfgame.controls.Action.NONE;

/**
 * @author Nicolas
 * @since 27/09/2017.
 */
public class InputController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InputController.class);
    @Nonnull
    private InputManager inputManager;
    @Nonnull
    private Collection<ControlListener> actionListeners = new ArrayList<>();

    public InputController(@Nonnull InputManager inputManager) {
        this.inputManager = inputManager;

        // init keys
        addKeyMapping(Action.LEFT, KeyInput.KEY_LEFT);
        addKeyMapping(Action.RIGHT, KeyInput.KEY_RIGHT);
        addKeyMapping(Action.FORWARD, KeyInput.KEY_UP);
        addKeyMapping(Action.BACKWARD, KeyInput.KEY_DOWN);

        ActionListener listener = (name, value, tpf) -> {
            final Action action = Action.forName(name);
            if (value && action != NONE) {
                actionListeners.stream()
                        .filter(ControlListener::isActive)
                        .forEach(l -> {
                            l.processAction(action);
                        });
            }
        };

        inputManager.addListener(listener, Action.names());
    }

    public void registerListener(@Nonnull ControlListener listener) {
        actionListeners.add(listener);
    }

    private void addKeyMapping(Action action, int key) {
        inputManager.addMapping(action.name(), new KeyTrigger(key));
    }

    @Override
    public String toString() {
        return "InputController{" +
                "inputManager=" + inputManager +
                '}';
    }
}
