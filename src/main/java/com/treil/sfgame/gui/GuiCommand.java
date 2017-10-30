package com.treil.sfgame.gui;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 18/10/2017.
 */
public enum GuiCommand {
    MAP_UPDATE, END_TURN,
    //
    ;

    public interface Listener {
        void processCommand(@Nonnull GuiCommand command);
    }
}
