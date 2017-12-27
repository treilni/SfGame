package com.treil.sfgame.player;

import com.treil.sfgame.units.Unit;
import com.treil.sfgame.units.UnitColor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas
 * @since 11/10/2017.
 */
public class Player {
    private boolean computerControlled = false;
    @Nonnull
    private String name;
    @Nonnull
    private final List<Unit> units = new ArrayList<>();
    @Nonnull
    private final UnitColor antColor;

    public Player(boolean computerControlled, @Nonnull String name, @Nonnull UnitColor antColor) {
        this.computerControlled = computerControlled;
        this.name = name;
        this.antColor = antColor;
    }

    public void addUnit(@Nonnull Unit unit) {
        units.add(unit);
    }

    @Nonnull
    public List<Unit> getUnits() {
        return units;
    }

    @Nonnull
    public String getName() {
        return name;
    }
}
