package com.treil.render.scene.decoration;

import com.treil.render.scene.common.Simple3dObject;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class Flower4 extends Simple3dObject {
    public Flower4(MaterialManager materialManager) {
        super(materialManager, "lowpoly/flower4_1.obj", 0.1f);
    }
}
