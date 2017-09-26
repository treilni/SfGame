package com.treil.sfgame.map;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexCell {
    private final int row;
    private final int column;

    public HexCell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
