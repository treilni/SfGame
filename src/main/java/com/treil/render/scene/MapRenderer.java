package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.treil.render.geom.Angle;
import com.treil.render.geom.HasExtent;
import com.treil.render.scene.tile.HexTile;
import com.treil.sfgame.map.HexMap;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
class MapRenderer implements HasExtent {
    private final AssetManager assetManager;
    private final HexMap map;
    private final List<HexTile> tiles = new ArrayList<>();
    private static final float hexRadius = 1f;

    MapRenderer(AssetManager assetManager, HexMap map) {
        this.assetManager = assetManager;
        this.map = map;

        Material firstMat = getUnshadedMaterial(assetManager, ColorRGBA.Cyan);
        Material tileMat = getUnshadedMaterial(assetManager, ColorRGBA.Green);
        Material borderMat = getUnshadedMaterial(assetManager, ColorRGBA.Red);
        final double smallRadius = hexRadius * Math.cos(Angle.DEG_30);
        float xStep = (float) (2 * smallRadius);
        float yStep = (float) (xStep * Math.sin(Angle.DEG_60));

        int rowCount = map.getRowCount();
        int colCount = map.getColCount();
        float y = 0f;
        for (int r = 0; r < rowCount; r++, y += yStep) {
            float x = r % 2 == 0 ? 0 : xStep / 2.0f;
            for (int c = 0; c < colCount; c++, x += xStep) {
                Material mat = r + c == 1 ? firstMat : tileMat;
                tiles.add(new HexTile(x, y, hexRadius, mat, borderMat));
            }
        }
    }

    private Material getUnshadedMaterial(AssetManager assetManager, ColorRGBA color) {
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        return mat;
    }

    @Nonnull
    List<HexTile> getTiles() {
        return tiles;
    }

    @Override
    public Vector3f getExtent() {
        final double smallRadius = hexRadius * Math.cos(Angle.DEG_30);
        float xStep = (float) (2 * smallRadius);
        float yStep = (float) (xStep * Math.sin(Angle.DEG_60));
        return new Vector3f(xStep * map.getColCount(), 0f, yStep * map.getRowCount());
    }
}
