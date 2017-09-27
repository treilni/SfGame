package com.treil.sfgame.map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexMap {
    @Nonnull
    final private List<HexRow> rows = new ArrayList<>();

    public HexMap(final int rowCount, final int columns) {
        for (int r = 0; r < rowCount; r++) {
            HexRow row = new HexRow(r, columns);
            rows.add(row);
        }
    }

    @Nullable
    public HexCell getCellAt(int row, int column) {
        if (row >= 0 && row < rows.size()) {
            HexRow cells = rows.get(row);
            if (column >= 0 && column < cells.size()) {
                return cells.get(column);
            }
        }
        return null;
    }

    @Nullable
    public HexCell getSibling(@Nonnull final HexCell cell, @Nonnull final HexDirection direction) {
        final int row = cell.getRow();
        final int column = cell.getColumn();
        int colOffset = 0;
        switch (direction) {
            case EAST:
                colOffset = 1;
                break;
            case WEST:
                colOffset = -1;
                break;
            case SOUTH_EAST:
            case NORTH_EAST:
                colOffset = row % 2 == 0 ? 0 : 1;
                break;
            case NORTH_WEST:
            case SOUTH_WEST:
                colOffset = row % 2 == 0 ? -1 : 0;
                break;
        }
        return getCellAt(row + direction.getRowOffset(), column + colOffset);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColCount() {
        return rows.size() > 0 ? rows.get(0).size() : 0;
    }
}
