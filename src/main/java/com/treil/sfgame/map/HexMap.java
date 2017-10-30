package com.treil.sfgame.map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexMap {
    @Nonnull
    final private List<HexRow> rows = new ArrayList<>();
    @Nonnull
    final private Map<HexCell, Integer> highlightedCells = new HashMap<>();
    private boolean highlightUpdated = false;

    public HexMap(final int rowCount, final int columns, @Nonnull MapGenerator mapGenerator) {
        for (int r = 0; r < rowCount; r++) {
            HexRow row = new HexRow(r, columns, mapGenerator);
            rows.add(row);
        }
    }

    @Nullable
    public HexCell getCellAt(@Nonnull MapLocation location) {
        return getCellAt(location.getRow(), location.getColumn());
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
        final MapLocation location = cell.getLocation();
        final int row = location.getRow();
        final int column = location.getColumn();
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
        final MapLocation siblingLocation = new MapLocation(row + direction.getRowOffset(), column + colOffset);
        return getCellAt(siblingLocation);
    }

    /**
     * @param startingCell the start location
     * @return a map of hexes indexing the cost needed to reach them
     */
    @Nonnull
    public Map<HexCell, Integer> getReachableCells(@Nonnull HexCell startingCell, int movementPoints) {
        Map<HexCell, Integer> result = new HashMap<>();
        getReachableCells(startingCell, 0, movementPoints, result);
        result.remove(startingCell);
        return result;
    }

    private void getReachableCells(HexCell startingCell, int startingCost, int movementPoints, Map<HexCell, Integer> result) {
        final Integer currentCost = result.get(startingCell);
        if (currentCost == null || currentCost > startingCost) {
            result.put(startingCell, startingCost);
            List<HexCell> siblings = getSiblings(startingCell);
            siblings.forEach(sibling -> {
                final int siblingCost = sibling.getTerrain().getMovementCost() + startingCost;
                if (siblingCost <= movementPoints) {
                    getReachableCells(sibling, siblingCost, movementPoints, result);
                }
            });
        }
    }

    @Nonnull
    private List<HexCell> getSiblings(@Nonnull HexCell startingCell) {
        List<HexCell> result = new ArrayList<>();
        for (HexDirection direction : HexDirection.values()) {
            final HexCell sibling = getSibling(startingCell, direction);
            if (sibling != null) {
                result.add(sibling);
            }
        }
        return result;
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColCount() {
        return rows.size() > 0 ? rows.get(0).size() : 0;
    }

    public void setHighlightedCells(@Nonnull Map<HexCell, Integer> cellMap) {
        highlightedCells.clear();
        highlightedCells.putAll(cellMap);
        highlightUpdated = true;
    }

    @Nonnull
    public Map<HexCell, Integer> getHighlightedCells() {
        return highlightedCells;
    }

    public boolean highlightHasChanged(boolean reset) {
        final boolean result = highlightUpdated;
        if (reset) {
            highlightUpdated = false;
        }
        return result;
    }
}
