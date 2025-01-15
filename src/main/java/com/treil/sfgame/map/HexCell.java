package com.treil.sfgame.map;


import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexCell {
    @Nonnull
    private final MapLocation location;
    @Nonnull
    private Terrain terrain;

    HexCell(int row, int column, MapGenerator mapGenerator) {
        this.location = new MapLocation(row, column);
        terrain = mapGenerator.getTerrain(row, column);
    }

    @Nonnull
    public MapLocation getLocation() {
        return location;
    }

    @Nonnull
    public Terrain getTerrain() {
        return terrain;
    }
}
