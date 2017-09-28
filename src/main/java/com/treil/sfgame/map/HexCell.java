package com.treil.sfgame.map;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexCell {
    private final int row;
    private final int column;
    @Nonnull
    private Terrain terrain;

    public HexCell(int row, int column, MapGenerator mapGenerator) {
        this.row = row;
        this.column = column;
        terrain = mapGenerator.getTerrain(row, column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Nonnull
    public Terrain getTerrain() {
        return terrain;
    }
}
