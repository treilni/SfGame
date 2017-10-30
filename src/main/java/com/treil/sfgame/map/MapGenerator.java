package com.treil.sfgame.map;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public interface MapGenerator {
    @Nonnull
    Terrain getTerrain(int row, int column);
}
