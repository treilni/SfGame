package com.treil.render.scene.units;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;

/**
 * @author Nicolas
 * @since 04/10/2017.
 */
public class Ant extends Unit {
    public Ant(AssetManager assetManager) {
        super(assetManager, "lowpoly/ant1.obj", 0.1f);
    }
}
