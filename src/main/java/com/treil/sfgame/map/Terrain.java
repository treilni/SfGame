package com.treil.sfgame.map;

import org.jetbrains.annotations.Contract;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public enum Terrain {
    GRASS(1), DIRT(1), WATER(99), SAND(2), FOREST(2),;

    private final int movementCost;

    Terrain(int movementCost) {
        this.movementCost = movementCost;
    }

    @Contract(pure = true)
    public int getMovementCost() {
        return movementCost;
    }
}
