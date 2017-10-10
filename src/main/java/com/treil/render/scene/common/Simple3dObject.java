package com.treil.render.scene.common;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public abstract class Simple3dObject extends Node {
    public Simple3dObject(MaterialManager materialManager, String name, float scale) {
        final Geometry model = (Geometry) materialManager.getAssetManager().loadModel(name);
        attachChild(model);
        setMaterial(materialManager.getLowPolyMaterial());
        scale(scale);
    }
}
