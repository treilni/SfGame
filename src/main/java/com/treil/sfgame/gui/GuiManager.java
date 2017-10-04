package com.treil.sfgame.gui;

import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

/**
 * @author Nicolas
 * @since 04/10/2017.
 */
public class GuiManager extends Container {
    public GuiManager(Node guiNode) {
        guiNode.attachChild(this);

// Put it somewhere that we will see it.
// Note: Lemur GUI elements grow down from the upper left corner.
        setLocalTranslation(300, 300, 0);

// Add some elements
        addChild(new Label("Hello, World."));
        Button clickMe = addChild(new Button("Click Me"));
        clickMe.addClickCommands((Command<Button>) source -> System.out.println("The world is yours."));
    }
}
