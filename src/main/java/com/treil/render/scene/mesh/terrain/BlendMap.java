package com.treil.render.scene.mesh.terrain;

import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;

/**
 * @author Nicolas
 * @since 15/02/2017.
 * <p>
 * A random map to blend textures or images. This produces values between 0 and 1
 */
public class BlendMap {
    private final double[][] values;
    private final int seed;
    private final int xExtent;
    private final int zExtent;
    private static final double DefaultFrequency = 0.3d;
    private double frequency = DefaultFrequency;
    private double sigmoidCoef = 1.0d;

    public BlendMap(int xExtent, int zExtent, int seed, double frequency) {
        this.frequency = frequency;
        this.xExtent = xExtent;
        this.zExtent = zExtent;
        this.seed = seed;
        values = new double[zExtent][xExtent];

        for (int z = 0; z < zExtent; z++) {
            for (int x = 0; x < xExtent; x++) {
                values[z][x] = getValue(x, z);
            }
        }
    }

    public BlendMap(int xExtent, int zExtent, int seed) {
        this(xExtent, zExtent, seed, DefaultFrequency);
    }

    private double getValue(int x, int z) {
        double coefs = 1.0d;
        double d = (noise(x, z, frequency) / coefs);
        return sigmoid(d, sigmoidCoef);
    }

    private double sigmoid(double d, double sigmoidCoef) {
        return 1.0d / (1 + Math.exp(-sigmoidCoef * d));
    }

    private double noise(float x, float z, double frequency) {
        return Noise.valueCoherentNoise3D(x * frequency, 0.0d, z * frequency, seed, NoiseQuality.STANDARD);
    }

    public double get(int x, int z) {
        if (x < 0) {
            x = 0;
        } else if (x >= xExtent) {
            x = xExtent - 1;
        }
        if (z < 0) {
            z = 0;
        } else if (z >= zExtent) {
            z = zExtent - 1;
        }
        return values[z][x];
    }
}
