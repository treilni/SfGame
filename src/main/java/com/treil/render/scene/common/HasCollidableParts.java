package com.treil.render.scene.common;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Nicolas
 * @since 19/10/2017.
 */
public interface HasCollidableParts {
    @Nonnull
    Set<Geometry> getCollidableParts();
}
