package com.treil.render.scene.mesh.terrain;

import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;

/**
 * @author Nicolas
 * @since 15/02/2017.
 * <p>
 * A random map to blend textures or images. This produces values between 0 and 1
 */
public class PlainNoiseMap implements NoiseMap {
    static final double DefaultFrequency = 0.3d;

    private final double frequency;
    private final int seed;

    public PlainNoiseMap(int seed, double frequency) {
        this.frequency = frequency;
        this.seed = seed;
    }

    public PlainNoiseMap(int seed) {
        this(seed, DefaultFrequency);
    }

    /**
     * returns a value between 0.0 and 1.0
     *
     * @param x
     * @param z
     * @return
     */
    public double getValueAt(double x, double z) {
        double coefs = 1.0d;
        double d = (noise(x, z, frequency) / coefs);
        return (d + 1d) / 2d;
    }

    private double noise(double x, double z, double frequency) {
        return Noise.valueCoherentNoise3D(x * frequency, 0.0d, z * frequency, seed, NoiseQuality.STANDARD);
    }
}
