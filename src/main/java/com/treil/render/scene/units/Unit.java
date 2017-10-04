package com.treil.render.scene.units;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public abstract class Unit extends Node {
    public Unit(AssetManager assetManager, String name, float scale) {
        final Spatial model = assetManager.loadModel(name);
        attachChild(model);
        setLocalScale(scale);
    }
}
