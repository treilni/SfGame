package com.treil.sfgame.units;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 27/12/2017.
 */
public enum UnitType {
    BEETLE, BLACK_ANT(UnitColor.BLACK), RED_ANT(UnitColor.RED), YELLOW_ANT(UnitColor.YELLOW);

    @Nonnull
    private final UnitColor color;

    UnitType(@Nonnull UnitColor color) {
        this.color = color;
    }

    UnitType() {
        color = UnitColor.UNDEFINED;
    }

    @Nonnull
    public UnitColor getColor() {
        return color;
    }
}
