package com.treil.sfgame.map;

import org.jetbrains.annotations.NotNull;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class SimpleMapGenerator implements MapGenerator {
    @NotNull
    @Override
    public Terrain getTerrain(int row, int column) {
        return row > 2 && row < 6 && column > 3 && column < 6 ? Terrain.DIRT : Terrain.GRASS;
    }
}
