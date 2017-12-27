package com.treil.sfgame.units;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 12/10/2017.
 */
public class Ant extends Unit {

    public Ant(@Nonnull UnitColor color) {
        super(getTypeFromColor(color));
        setMovementPoints(4);
    }

    @Nonnull
    private static UnitType getTypeFromColor(@Nonnull UnitColor color) {
        switch (color) {
            case RED:
                return UnitType.RED_ANT;
            case YELLOW:
                return UnitType.YELLOW_ANT;
            default:
                return UnitType.BLACK_ANT;
        }
    }
}
