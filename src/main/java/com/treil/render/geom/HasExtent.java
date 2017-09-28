package com.treil.render.geom;

import com.jme3.math.Vector3f;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 27/09/2017.
 */
public interface HasExtent {
    @NotNull
    @Nonnull
    Vector3f getExtent();
}
