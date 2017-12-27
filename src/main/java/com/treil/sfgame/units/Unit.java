package com.treil.sfgame.units;

import com.treil.sfgame.map.HexCell;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 12/10/2017.
 */
public class Unit {
    private int movementPoints = 0;
    private HexCell position;
    private boolean selected = false;
    @Nonnull
    private final UnitType type;

    Unit(@Nonnull UnitType type) {
        this.type = type;
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public void setPosition(@Nonnull HexCell position) {
        this.position = position;
    }

    public HexCell getPosition() {
        return position;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getMovementPoints() {
        return movementPoints;
    }

    public boolean isSelected() {
        return selected;
    }

    @Nonnull
    public UnitType getType() {
        return type;
    }
}
