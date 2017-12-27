package com.treil.render.scene.units;

import com.jme3.asset.AssetManager;
import com.treil.sfgame.units.UnitType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nicolas
 * @since 04/10/2017.
 */
public class AntSprite extends UnitSprite {
    public AntSprite(AssetManager assetManager, UnitType unitType) {
        super(assetManager, assetNameFromType(unitType), 0.1f);
    }

    @NotNull
    private static String assetNameFromType(UnitType unitType) {
        switch (unitType) {
            case RED_ANT:
                return "lowpoly/ant1.obj";
            case YELLOW_ANT:
                return "lowpoly/ant3.obj";
            default:
                return "lowpoly/ant2.obj";
        }
    }


}
