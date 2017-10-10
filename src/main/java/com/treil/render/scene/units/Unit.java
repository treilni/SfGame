package com.treil.render.scene.units;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.treil.sfgame.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public abstract class Unit extends Node {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Unit(AssetManager assetManager, String name, float scale) {
        final Spatial model = assetManager.loadModel(name);
        attachChild(model);
        setLocalScale(scale);
//        // center
//        BoundingVolume worldBound = getWorldBound();
//        logger.info("Bound : " + worldBound);
//        setLocalTranslation(worldBound.getCenter().divideLocal(-1.f));
//        worldBound = getWorldBound();
//        logger.info("Bound now : " + worldBound);
    }
}
