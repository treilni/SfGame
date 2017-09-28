package com.treil.sfgame.map;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public interface MapGenerator {
    @NotNull
    @Nonnull
    Terrain getTerrain(int row, int column);
}
