package com.treil.sfgame.map;

/**
 * @author Nicolas
 * @since 12/10/2017.
 */
public class MapLocation {
    private int row;
    private int column;

    public MapLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MapLocation that = (MapLocation) o;

        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "MapLocation{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    public MapLocation add(int row, int column) {
        return new MapLocation(this.getRow() + row, this.getColumn() + column);
    }
}
