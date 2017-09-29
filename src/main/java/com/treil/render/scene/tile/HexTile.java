package com.treil.render.scene.tile;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.treil.render.scene.DecorationManager;
import com.treil.render.scene.mesh.Hex;
import com.treil.render.scene.mesh.HexBorder;
import com.treil.sfgame.map.Terrain;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Nicolas
 * @since 25/09/2017.
 */
public class HexTile extends Node {
    private static final Hex tilePrimitive = new Hex(0f, 0f, 1f);
    private static final HexBorder borderPrimitive = new HexBorder(0f, 0f, 1f, 2f);

    public HexTile(float x, float y, float radius,
                   @Nonnull Material tileMaterial, @Nonnull Material borderMaterial) {
        Geometry tileGeom = new Geometry("map", tilePrimitive);
        tileGeom.setMaterial(tileMaterial);

        Geometry borderGeom = new Geometry("map", borderPrimitive);
        borderGeom.setMaterial(borderMaterial);

        attachChild(tileGeom);
        attachChild(borderGeom);
        setLocalTranslation(x, 0f, y);
        setLocalScale(radius);
    }

    @Nonnull
    public void addDecorations(@Nonnull DecorationManager decorationManager, @Nonnull Terrain terrain) {
        List<Node> decorations = decorationManager.getDecorations(terrain);
        decorations.forEach(this::attachChild);
    }
}
