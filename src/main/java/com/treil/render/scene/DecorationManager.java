package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.treil.render.geom.Angle;
import com.treil.render.scene.decoration.*;
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
    private static final int GrassPercent = 50;
    private static final int GrassMax = 4;

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
                mushroom.setLocalTranslation(randomCoordFromCenter(), 0f, randomCoordFromCenter());
                result.add(mushroom);
            } else if (random.nextInt(100) < LogPercent) {
                final Log log = new Log(materialManager);
                randomRotateAroundY(log);
                log.setLocalTranslation(randomCoordFromCenter(), 0f, randomCoordFromCenter());
                result.add(log);
            } else {
                for (int i = 0; i < GrassMax; i++) {
                    if (random.nextInt(100) < GrassPercent) {
                        final Decoration grass = random.nextInt(100) < 50 ? new Grass1(materialManager) : new Grass2(materialManager);
                        randomRotateAroundY(grass);
                        grass.setLocalTranslation(randomCoordFromCenter(0.9f), 0f, randomCoordFromCenter(0.9f));
                        result.add(grass);
                    }
                }
            }
        } else if (terrain == Terrain.FOREST) {
            final Decoration tree = new Pine(materialManager);
            tree.setLocalScale((1f - 0.4f * random.nextFloat()) * 0.6f);
            randomRotateAroundY(tree);
            tree.setLocalTranslation(randomCoordFromCenter(), 0f, randomCoordFromCenter());
            result.add(tree);
        }
        return result;
    }

    private float randomCoordFromCenter(float maxDist) {
        return (random.nextFloat() - 0.5f) * 2f * maxDist;
    }

    private float randomCoordFromCenter() {
        return randomCoordFromCenter(0.5f);
    }

    private void randomRotateAroundY(Spatial spatial) {
        spatial.setLocalRotation(new Quaternion().fromAngles(0f, (float) (random.nextDouble() * Angle.PI * 2d), 0f));
    }
}
