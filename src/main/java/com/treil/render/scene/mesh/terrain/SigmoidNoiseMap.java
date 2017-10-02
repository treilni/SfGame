package com.treil.render.scene.mesh.terrain;

import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;

/**
 * @author Nicolas
 * @since 15/02/2017.
 * <p>
 * A random map to blend textures or images. This produces values between 0 and 1
 */
public class SigmoidNoiseMap extends PlainNoiseMap {
    private final double sigmoidCoef = 1.0d;

    public SigmoidNoiseMap(int seed, double frequency) {
        super(seed, frequency);
    }

    public SigmoidNoiseMap(int seed) {
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
        double d = super.getValueAt(x, z);
        return sigmoid(d, sigmoidCoef);
    }

    private double sigmoid(double d, double sigmoidCoef) {
        return 1.0d / (1 + Math.exp(-sigmoidCoef * d));
    }
}
