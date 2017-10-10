package com.treil.render.scene.decoration;

import com.treil.render.scene.common.Simple3dObject;
import com.treil.render.scene.material.MaterialManager;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class Pine extends Simple3dObject {
    public Pine(MaterialManager materialManager) {
        super(materialManager, "lowpoly/pine1_1.obj", 1f);
    }
}
