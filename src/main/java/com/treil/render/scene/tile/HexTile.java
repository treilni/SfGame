package com.treil.render.scene.tile;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.sun.istack.internal.NotNull;
import com.treil.render.scene.mesh.Hex;
import com.treil.render.scene.mesh.HexBorder;

/**
 * @author Nicolas
 * @since 25/09/2017.
 */
public class HexTile {
    private static final Hex tilePrimitive = new Hex(0f, 0f, 1f);
    private static final HexBorder borderPrimitive = new HexBorder(0f, 0f, 1f, 2f);
    private Geometry tileGeom;
    private Geometry borderGeom;

    public HexTile(float x, float y, float radius,
                   @NotNull Material tileMaterial, @NotNull Material borderMaterial) {
        tileGeom = new Geometry("map", tilePrimitive);
        tileGeom.setLocalTranslation(x, 0f, y);
        tileGeom.setLocalScale(radius);
        tileGeom.setMaterial(tileMaterial);

        borderGeom = new Geometry("map", borderPrimitive);
        borderGeom.setLocalTranslation(x, 0f, y);
        borderGeom.setLocalScale(radius);
        borderGeom.setMaterial(borderMaterial);
    }

    public void attachAsChild(@NotNull Node node) {
        node.attachChild(tileGeom);
        node.attachChild(borderGeom);
    }
}
