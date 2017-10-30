package com.treil.render.scene.tile;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.treil.render.scene.mesh.HexBorder;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 17/10/2017.
 */
public class SelectionMarker extends Geometry {
    private static final float DEFAULT_HEIGHT_OFFSET = 0.00002f;
    @Nonnull
    private static final HexBorder selectionMesh = new HexBorder(0f, 0f, 0.9f, 8f, DEFAULT_HEIGHT_OFFSET);

    public SelectionMarker(@Nonnull Material material) {
        super("selection");
        setMesh(makeSelectionMesh());
        setMaterial(material);
    }

    @Contract(pure = true)
    @Nonnull
    private Mesh makeSelectionMesh() {
        return selectionMesh;
    }
}