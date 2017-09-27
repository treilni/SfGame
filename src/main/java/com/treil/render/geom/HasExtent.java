package com.treil.render.geom;

import com.jme3.math.Vector3f;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 27/09/2017.
 */
public interface HasExtent {
    @Nonnull
    Vector3f getExtent();
}
