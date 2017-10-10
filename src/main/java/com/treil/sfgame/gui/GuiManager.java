package com.treil.sfgame.gui;

import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.treil.render.geom.Size2D;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicolas
 * @since 04/10/2017.
 */
public class GuiManager extends Container {
    private static final int ViewportHeightPercent = 15;

    public enum State {
        WELCOME, SETUP, MAIN_MENU, OVERWORLD, UNDERWORLD;
    }

    @Nonnull
    private final Size2D size;

    @Nonnull
    private State state = State.WELCOME;

    @Nonnull
    private final Map<State, Container> uiFromState = new HashMap<>();

    public GuiManager(@Nonnull Node guiNode, @Nonnull Size2D viewportSize) {
        guiNode.attachChild(this);

        // Place at bottom of the viewport
        this.size = new Size2D(viewportSize.getWidth(), viewportSize.getHeight() * ViewportHeightPercent / 100);
        setLocalTranslation(0, size.getHeight(), 0);

        // Add some elements

        updateState();
    }

    private void updateState() {
        clearChildren();
        addChild(uiFromState.computeIfAbsent(state, newState -> {
            Container container = new Container();
            switch (newState) {
                case WELCOME:
                case SETUP:
                case MAIN_MENU:
                case OVERWORLD:
                case UNDERWORLD:
                    container.addChild(new Label("Hello, World."));
                    Button clickMe = addChild(new Button("Click Me"));
                    clickMe.addClickCommands((Command<Button>) source -> System.out.println("The world is yours."));
                    break;
            }
            return container;
        }));
    }

    @Override
    public String toString() {
        return "GuiManager{}";
    }
}
