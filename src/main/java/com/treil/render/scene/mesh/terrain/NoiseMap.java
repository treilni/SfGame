package com.treil.render.scene.mesh.terrain;

/**
 * @author Nicolas
 * @since 15/02/2017.
 * <p>
 * A random map to blend textures or images. This produces values between 0 and 1
 */
public interface NoiseMap {
    double getValueAt(double x, double z);
}
