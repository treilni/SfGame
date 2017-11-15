package com.treil.render.scene.mesh.terrain;

import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;

/**
 * @author Nicolas
 * @since 31/01/2017.
 */
public class RandomTerrainMesh extends TerrainMesh {
    public static final int DEFAULT_SEED = 3874368;
    private int seed;
    @SuppressWarnings("FieldCanBeLocal")
    private float maxHeight = 5.0f;
    @SuppressWarnings("FieldCanBeLocal")
    private double frequency = 0.05F;
    @SuppressWarnings("FieldCanBeLocal")
    private double flatness = 0.8F;

    public RandomTerrainMesh(int xExtent, int zExtent, float step) {
        this(xExtent, zExtent, step, DEFAULT_SEED);
    }

    public RandomTerrainMesh(int xExtent, int zExtent, float step, int seed) {
        super(xExtent, zExtent, step, false);
        this.seed = seed;
        init();
    }

    @Override
    public float initElevation(float x, float z) {
        double coefs = 1 + 1 / 2.0d + 1 / 4.0d;
        double d = (noise(x, z, frequency) +
                noise(x, z, frequency * 2.0d) / 2.0d +
                noise(x, z, frequency * 4.0d) / 4.0d)
                / coefs;
        double normalizedHeight = (d + 1.0d) / 2.0d;
        return (float) (maxHeight * Math.pow(normalizedHeight, flatness));
    }

    private double noise(float x, float z, double frequency) {
        return Noise.valueCoherentNoise3D(x * frequency, 0.0d, z * frequency, seed, NoiseQuality.STANDARD);
    }


}
