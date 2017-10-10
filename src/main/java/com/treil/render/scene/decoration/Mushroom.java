package com.treil.render.scene.decoration;

import com.treil.render.scene.common.Simple3dObject;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class Mushroom extends Simple3dObject {
    public Mushroom(MaterialManager materialManager) {
        super(materialManager, "lowpoly/mushroom1_1.obj", 0.1f);
    }
}
