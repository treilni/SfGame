package com.treil.sfgame.map;

import com.treil.render.scene.mesh.terrain.NoiseMap;
import com.treil.render.scene.mesh.terrain.PlainNoiseMap;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class RandomMapGenerator implements MapGenerator {
    private static final int DefaultSeed = 12345;
    private static final double WaterMark = 0.1;
    private static final double SandMark = 0.15;
    private static final double ForestMark = 0.3;

    private final NoiseMap heightMap;
    private final NoiseMap forestMap;

    public RandomMapGenerator() {
        this(DefaultSeed);
    }

    public RandomMapGenerator(int seed) {
        Random random = new Random(seed);
        heightMap = new PlainNoiseMap(random.nextInt());
        forestMap = new PlainNoiseMap(random.nextInt());
    }

    @Nonnull
    @Override
    public Terrain getTerrain(int row, int column) {
        double x = column + 0.5d * (row % 2);
        double z = row;

        final double height = heightMap.getValueAt(x, z);
        if (height < WaterMark) {
            return Terrain.WATER;
        } else if (height < SandMark) {
            return Terrain.SAND;
        }
        final double isForest = forestMap.getValueAt(x, z);
        if (isForest < ForestMark) {
            return Terrain.FOREST;
        }
        return Terrain.GRASS;
    }
}
