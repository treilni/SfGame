package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.treil.render.geom.HasExtent;
import com.treil.sfgame.map.HexMap;

/**
 * @author Nicolas
 * @since 24/01/2017.
 */
public interface Scene extends HasExtent {
    void init(SimpleApplication application, HexMap map);

    void update(float tpf, SimpleApplication application);

}
