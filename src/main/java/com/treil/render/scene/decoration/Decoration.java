package com.treil.render.scene.decoration;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.util.TangentBinormalGenerator;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public abstract class Decoration extends Node {
    public Decoration(MaterialManager materialManager, String name, float scale) {
        final Geometry model = (Geometry) materialManager.getAssetManager().loadModel(name);
        model.setMaterial(materialManager.getLowPolyMaterial());
        attachChild(model);
        setLocalScale(scale);
    }
}
