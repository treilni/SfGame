package com.treil.render.scene.decoration;

import com.treil.render.scene.common.Simple3dObject;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class Grass2 extends Simple3dObject {
    public Grass2(MaterialManager materialManager) {
        super(materialManager, "lowpoly/grass2_1.obj", 0.1f);
    }
}
