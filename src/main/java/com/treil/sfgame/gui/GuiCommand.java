package com.treil.sfgame.gui;

import com.treil.sfgame.messaging.EventListener;
import com.treil.sfgame.messaging.guiEvents.NextTurnEvent;

/**
 * @author Nicolas
 * @since 18/10/2017.
 */
public enum GuiCommand {
    END_TURN,
    //
    ;

    public interface Listener extends EventListener<NextTurnEvent> {
    }
}
