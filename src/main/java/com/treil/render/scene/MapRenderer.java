package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.treil.render.geom.Angle;
import com.treil.render.geom.HasExtent;
import com.treil.render.scene.tile.HexTile;
import com.treil.render.scene.tile.SelectionMarker;
import com.treil.sfgame.map.HexCell;
import com.treil.sfgame.map.HexMap;
import com.treil.sfgame.map.MapLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
class MapRenderer implements HasExtent {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(MapRenderer.class);

    private static final float hexRadius = 1f;
    private static final ColorRGBA grassColor = new ColorRGBA(ColorRGBA.Green).interpolateLocal(ColorRGBA.DarkGray, 0.25f);
    private static final ColorRGBA sandColor = new ColorRGBA(ColorRGBA.Yellow).interpolateLocal(ColorRGBA.White, 0.25f);
    private static final ColorRGBA forestColor = new ColorRGBA(ColorRGBA.Green).interpolateLocal(ColorRGBA.DarkGray, 0.4f);

    @Nonnull
    private final HexMap map;
    @Nonnull
    private final Node rootNode;
    @Nonnull
    private final Map<MapLocation, HexTile> locationToTile = new HashMap<>();
    @Nonnull
    private final Map<Spatial, HexTile> collidableToTile = new HashMap<>();
    @Nonnull
    private final SelectionMarker hexHoverNode;
    @Nonnull
    private final Set<SelectionMarker> usedHighlightMarkers = new HashSet<>();
    @Nonnull
    private final LinkedList<SelectionMarker> freeHighlightMarkers = new LinkedList<>();
    @Nonnull
    private final AssetManager assetManager;

    MapRenderer(@Nonnull AssetManager assetManager, @Nonnull HexMap map, @Nonnull Node rootNode) {
        this.map = map;
        this.rootNode = rootNode;
        this.assetManager = assetManager;

        Material borderMat = getUnshadedMaterial(ColorRGBA.Red);
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
                    setTileLocation(r, c, tile);
                    tile.getCollidableParts().forEach(spatial -> collidableToTile.put(spatial, tile));
                    tile.addDecorations(decorationManager, cell.getTerrain());
                    rootNode.attachChild(tile);
                }
            }
        }

        final Material selectionMaterial = getUnshadedMaterial(Colors.hexHoverColor);
        hexHoverNode = new SelectionMarker(selectionMaterial);

    }

    private void setTileLocation(int r, int c, @Nonnull HexTile tile) {
        final MapLocation location = new MapLocation(r, c);
        locationToTile.put(location, tile);
        tile.setLocation(location);
    }

    private Material getTileMat(AssetManager assetManager, HexCell cell) {
        final ColorRGBA color;
        switch (cell.getTerrain()) {
            case GRASS:
                color = grassColor;
                break;
            case DIRT:
                color = ColorRGBA.Brown;
                break;
            case SAND:
                color = sandColor;
                break;
            case WATER:
                color = ColorRGBA.Cyan;
                break;
            case FOREST:
                color = forestColor;
                break;
            default:
                color = ColorRGBA.Red;
                break;
        }
        return getUnshadedMaterial(color);
    }

    Material getUnshadedMaterial(ColorRGBA color) {
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        return mat;
    }

    @Nonnull
    @Override
    public Vector3f getExtent() {
        final double smallRadius = hexRadius * Math.cos(Angle.DEG_30);
        float xStep = (float) (2 * smallRadius);
        float yStep = (float) (xStep * Math.sin(Angle.DEG_60));
        return new Vector3f(xStep * map.getColCount(), 0f, yStep * map.getRowCount());
    }

    HexTile getTileAt(@Nonnull MapLocation location) {
        return locationToTile.get(location);
    }

    HexTile getTileAt(@Nonnull CollisionResults results) {
        int n = results.size();
        for (int i = 0; i < n; i++) {
            final CollisionResult collision = results.getCollision(i);
            final HexTile hexTile = collidableToTile.get(collision.getGeometry());
            if (hexTile != null) {
                return hexTile;
            }
        }
        return null;
    }

    void showPointedTile(@Nullable HexTile tileUnderCursor) {
        if (tileUnderCursor != null) {
            rootNode.attachChild(hexHoverNode);
            final Vector3f markerTranslation = tileUnderCursor.getWorldTranslation();
            hexHoverNode.setLocalTranslation(markerTranslation);
        } else {
            hexHoverNode.removeFromParent();
        }
    }

    private void highliteCellSet(@Nonnull Set<HexCell> cellSet) {
        usedHighlightMarkers.forEach(marker -> {
            marker.removeFromParent();
            freeHighlightMarkers.push(marker);
        });
        usedHighlightMarkers.clear();
        cellSet.forEach(cell -> {
            final HexTile tile = getTileAt(cell.getLocation());
            if (tile != null) {
                final SelectionMarker marker;
                if (!freeHighlightMarkers.isEmpty()) {
                    marker = freeHighlightMarkers.pop();
                } else {
                    marker = createSelectionMarker();
                }
                usedHighlightMarkers.add(marker);
                final Vector3f markerTranslation = tile.getWorldTranslation();
                marker.setLocalTranslation(markerTranslation);
                rootNode.attachChild(marker);
            }
        });
    }

    @Nonnull
    private SelectionMarker createSelectionMarker() {
        final SelectionMarker result = new SelectionMarker(getUnshadedMaterial(Colors.highlightedCellColor));
        result.scale(0.9f);
        return result;
    }

    void renderMapUpdate() {
        if (map.highlightHasChanged(true)) {
            highliteCellSet(map.getHighlightedCells());
        }
    }
}