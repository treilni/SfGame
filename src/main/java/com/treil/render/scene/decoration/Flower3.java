package com.treil.render.scene.decoration;

import com.treil.render.scene.common.Simple3dObject;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class Flower3 extends Simple3dObject {
    public Flower3(MaterialManager materialManager) {
        super(materialManager, "lowpoly/flower3_1.obj", 0.1f);
    }
}
