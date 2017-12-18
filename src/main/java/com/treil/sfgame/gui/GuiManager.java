package com.treil.sfgame.gui;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.treil.render.geom.Size2D;
import com.treil.sfgame.messaging.XEventBus;
import com.treil.sfgame.messaging.guiEvents.NextTurnEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Nicolas
 * @since 04/10/2017.
 */
public class GuiManager extends Container {
    private static final int ViewportHeightPercent = 15;

    @Nonnull
    private ResourceBundle resourceBundle;

    public enum State {
        WELCOME, SETUP, MAIN_MENU, OVERWORLD, UNDERWORLD
    }

    @Nonnull
    private final Size2D size;

    @Nonnull
    private State state = State.WELCOME;

    @Nonnull
    private final Map<State, Container> uiFromState = new HashMap<>();

    public GuiManager(@Nonnull Node guiNode, @Nonnull Size2D viewportSize) {
        resourceBundle = initLanguageBundle();
        guiNode.attachChild(this);

        // Place at bottom of the viewport
        this.size = new Size2D(viewportSize.getWidth(), viewportSize.getHeight() * ViewportHeightPercent / 100);
        setLocalTranslation(0, size.getHeight(), 0);
        setPreferredSize(new Vector3f(size.getWidth(), size.getHeight(), 0f));

        // Add some elements

        updateState();
    }

    @Nonnull
    private ResourceBundle initLanguageBundle() {
        Locale locale = new Locale("fr", "FR");
        return ResourceBundle.getBundle("i18n", locale);
    }

    private void updateState() {
        clearChildren();
        addChild(uiFromState.computeIfAbsent(state, newState -> {
            Container container = new Container();
            switch (newState) {
                case WELCOME:
                    initWelcomeUI(container);
                    break;
                case SETUP:
                case MAIN_MENU:
                case OVERWORLD:
                case UNDERWORLD:
                    break;
            }
            return container;
        }));
    }

    private void initWelcomeUI(Container container) {
        container.addChild(new Label(resourceBundle.getString("game.title")));
        Button nextTurn = container.addChild(new Button(resourceBundle.getString("next.turn")));
        nextTurn.addClickCommands(button -> {
            XEventBus.fireEvent(new NextTurnEvent());
        });
    }

    @Override
    public String toString() {
        return "GuiManager{}";
    }
}
