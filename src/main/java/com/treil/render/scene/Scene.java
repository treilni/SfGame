package com.treil.render.scene;

import com.jme3.app.SimpleApplication;

/**
 * @author Nicolas
 * @since 24/01/2017.
 */
public interface Scene {
    void init(SimpleApplication application);

    void update(float tpf, SimpleApplication application);

}
