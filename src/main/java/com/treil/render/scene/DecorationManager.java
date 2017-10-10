package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import com.treil.render.geom.Angle;
import com.treil.render.scene.decoration.*;
import com.treil.render.scene.material.MaterialManager;
import com.treil.render.scene.units.Ant;
import com.treil.render.scene.units.Unit;
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

    enum Asset {
        GRASS1, GRASS2, LOG, MUSHROOM, PINE, ANT
    }

    @Nonnull
    private Random random = new Random(123L);

    @Nonnull
    private final MaterialManager materialManager;

    DecorationManager(@Nonnull AssetManager assetManager) {
        materialManager = new MaterialManager(assetManager);
    }

    @Nonnull
    public List<Spatial> getDecorations(@Nonnull Terrain terrain) {
        List<Spatial> result = new ArrayList<>();
        if (terrain == Terrain.GRASS) {
            if (random.nextInt(100) < MushroomPercent) {
                final Spatial mushroom = getAssetSpatial(Asset.MUSHROOM);
                randomRotateAroundY(mushroom);
                mushroom.move(randomCoordFromCenter(), 0f, randomCoordFromCenter());
                result.add(mushroom);
            } else if (random.nextInt(100) < LogPercent) {
                final Spatial log = getAssetSpatial(Asset.LOG);
                randomRotateAroundY(log);
                log.move(randomCoordFromCenter(), 0f, randomCoordFromCenter());
                result.add(log);
            } else {
                for (int i = 0; i < GrassMax; i++) {
                    if (random.nextInt(100) < GrassPercent) {
                        final Spatial grass = getAssetSpatial(random.nextInt(100) < 50 ? Asset.GRASS1 : Asset.GRASS2);
                        randomRotateAroundY(grass);
                        grass.move(randomCoordFromCenter(0.9f), 0f, randomCoordFromCenter(0.9f));
                        result.add(grass);
                    }
                }
            }
        } else if (terrain == Terrain.FOREST) {
            final Spatial tree = getAssetSpatial(Asset.PINE);
            tree.scale((1f - 0.4f * random.nextFloat()) * 0.6f);
            randomRotateAroundY(tree);
            tree.move(randomCoordFromCenter(), 0f, randomCoordFromCenter());
            result.add(tree);
        } else if (terrain == Terrain.WATER) {
            final Unit ant = new Ant(materialManager.getAssetManager());
            randomRotateAroundY(ant);
            result.add(ant);
        }
        return result;
    }

    private Spatial getAssetSpatial(Asset asset) {
        switch (asset) {
            case GRASS1:
                return new Grass1(materialManager);
            case GRASS2:
                return new Grass2(materialManager);
            case LOG:
                return new Log(materialManager);
            case MUSHROOM:
                return new Mushroom(materialManager);
            case PINE:
                return new Pine(materialManager);
            case ANT:
                return new Ant(materialManager.getAssetManager());
        }
        return new Mushroom(materialManager);
    }

    private float randomCoordFromCenter(float maxDist) {
        return (random.nextFloat() - 0.5f) * 2f * maxDist;
    }

    private float randomCoordFromCenter() {
        return randomCoordFromCenter(0.5f);
    }

    private void randomRotateAroundY(Spatial spatial) {
        spatial.rotate(new Quaternion().fromAngles(0f, (float) (random.nextDouble() * Angle.PI * 2d), 0f));
    }
}
