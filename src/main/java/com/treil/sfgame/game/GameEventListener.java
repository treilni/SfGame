package com.treil.sfgame.game;

import com.treil.sfgame.units.Unit;

/**
 * @author Nicolas
 * @since 30/10/2017.
 */
public interface GameEventListener {
    void onUnitUpdate(Unit unit);
}
