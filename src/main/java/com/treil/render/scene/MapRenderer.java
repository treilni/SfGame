package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.treil.render.geom.Angle;
import com.treil.render.geom.HasExtent;
import com.treil.render.scene.tile.HexTile;
import com.treil.sfgame.map.HexCell;
import com.treil.sfgame.map.HexMap;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
class MapRenderer implements HasExtent {
    private static final float hexRadius = 1f;

    @Nonnull
    private final HexMap map;
    @Nonnull
    private final List<HexTile> tiles = new ArrayList<>();

    MapRenderer(@Nonnull AssetManager assetManager, @Nonnull HexMap map) {
        this.map = map;

        Material borderMat = getUnshadedMaterial(assetManager, ColorRGBA.Red);
        final double smallRadius = hexRadius * Math.cos(Angle.DEG_30);
        float xStep = (float) (2 * smallRadius);
        float yStep = (float) (xStep * Math.sin(Angle.DEG_60));
        DecorationManager decorationManager = new DecorationManager(assetManager);

        int rowCount = map.getRowCount();
        int colCount = map.getColCount();
        float y = 0f;
        for (int r = 0; r < rowCount; r++, y += yStep) {
            float x = r % 2 == 0 ? 0 : xStep / 2.0f;
            for (int c = 0; c < colCount; c++, x += xStep) {
                final HexCell cell = map.getCellAt(r, c);
                if (cell != null) {
                    Material tileMat = getTileMat(assetManager, cell);
                    final HexTile tile = new HexTile(x, y, hexRadius, tileMat, borderMat);
                    tile.addDecorations(decorationManager, cell.getTerrain());
                    tiles.add(tile);
                }
            }
        }
    }

    private Material getTileMat(AssetManager assetManager, HexCell cell) {
        final ColorRGBA color;
        switch (cell.getTerrain()) {
            case GRASS:
                color = new ColorRGBA(ColorRGBA.Green).interpolateLocal(ColorRGBA.DarkGray, 0.25f);
                break;
            case DIRT:
                color = ColorRGBA.Brown;
                break;
            case WATER:
                color = ColorRGBA.Cyan;
                break;
            case FOREST:
                color = ColorRGBA.Green.interpolateLocal(ColorRGBA.DarkGray, 0.3f);
                break;
            default:
                color = ColorRGBA.Red;
                break;
        }
        return getUnshadedMaterial(assetManager, color);
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

    @NotNull
    @Override
    public Vector3f getExtent() {
        final double smallRadius = hexRadius * Math.cos(Angle.DEG_30);
        float xStep = (float) (2 * smallRadius);
        float yStep = (float) (xStep * Math.sin(Angle.DEG_60));
        return new Vector3f(xStep * map.getColCount(), 0f, yStep * map.getRowCount());
    }
}
