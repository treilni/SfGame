package com.treil.render.scene.tile;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.sun.istack.internal.NotNull;
import com.treil.render.geom.Hex;
import com.treil.render.geom.HexBorder;

/**
 * @author Nicolas
 * @since 25/09/2017.
 */
public class HexTile {
    private Hex hex;
    private Geometry tileGeom;
    private Geometry borderGeom;

    public HexTile(float x, float y, float radius,
                   @NotNull Material tileMaterial, @NotNull Material borderMaterial) {
        Hex h = new Hex(x, y, radius);
        tileGeom = new Geometry("map", h);
        tileGeom.setMaterial(tileMaterial);

        HexBorder hb = new HexBorder(x, y, radius, 5);
        borderGeom = new Geometry("map", hb);
        borderGeom.setMaterial(borderMaterial);
    }

    public void attachAsChild(@NotNull Node node) {
        node.attachChild(tileGeom);
        node.attachChild(borderGeom);
    }
}
