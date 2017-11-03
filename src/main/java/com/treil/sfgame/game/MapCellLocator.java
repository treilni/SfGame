package com.treil.sfgame.game;

import com.treil.sfgame.map.MapLocation;

import javax.annotation.Nullable;

/**
 * @author Nicolas
 * @since 03/11/2017.
 */
public interface MapCellLocator {
    @Nullable
    MapLocation getLocationUnderCursor();
}
