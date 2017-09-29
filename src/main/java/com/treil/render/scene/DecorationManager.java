package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.treil.render.geom.Angle;
import com.treil.render.scene.decoration.Log;
import com.treil.render.scene.decoration.Mushroom;
import com.treil.render.scene.material.MaterialManager;
import com.treil.sfgame.map.Terrain;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class DecorationManager {
    private static final int MushroomPercent = 5;
    private static final int LogPercent = 3;

    @Nonnull
    private Random random = new Random(123L);
    @Nonnull
    private final MaterialManager materialManager;

    public DecorationManager(@Nonnull AssetManager assetManager) {
        materialManager = new MaterialManager(assetManager);
    }

    @Nonnull
    public List<Node> getDecorations(@Nonnull Terrain terrain) {
        List<Node> result = new ArrayList<>();
        if (terrain == Terrain.GRASS) {
            if (random.nextInt(100) < MushroomPercent) {
                final Mushroom mushroom = new Mushroom(materialManager);
                randomRotateAroundY(mushroom);
                mushroom.setLocalTranslation(random.nextFloat() - 0.5f, 0f, random.nextFloat() - 0.5f);
                result.add(mushroom);
            }
            if (random.nextInt(100) < LogPercent) {
                final Log log = new Log(materialManager);
                randomRotateAroundY(log);
                log.setLocalTranslation(random.nextFloat() - 0.5f, 0f, random.nextFloat() - 0.5f);
                result.add(log);
            }
        }
        return result;
    }

    private void randomRotateAroundY(Spatial spatial) {
        spatial.setLocalRotation(new Quaternion(0f, 1f, 0f, (float) (random.nextDouble() * Angle.PI * 2L)));
    }
}
