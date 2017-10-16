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

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public void setPosition(@Nonnull HexCell position) {
        this.position = position;
    }

    public HexCell getPosition() {
        return position;
    }
}
