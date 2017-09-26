package com.treil.sfgame.map;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public enum HexDirection {
    EAST(true, 0),
    NORTH_EAST(true, -1), NORTH_WEST(false, -1),
    WEST(false, 0),
    SOUTH_WEST(false, 1), SOUTH_EAST(true, 1);

    private final boolean isEast;
    private final int rowOffset;

    HexDirection(boolean isEast, int rowOffset) {
        this.isEast = isEast;
        this.rowOffset = rowOffset;
    }

    public boolean isEast() {
        return isEast;
    }

    public int getRowOffset() {
        return rowOffset;
    }
}
