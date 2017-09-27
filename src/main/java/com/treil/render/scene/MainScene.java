package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.sun.istack.internal.NotNull;
import com.treil.render.geom.Angle;
import com.treil.render.scene.tile.HexTile;
import com.treil.sfgame.map.HexMap;

import java.util.List;

/**
 * @author Nicolas
 * @since 13/09/2017.
 */
public class MainScene implements Scene {
    public void init(SimpleApplication application, HexMap map) {
        AssetManager assetManager = application.getAssetManager();

        MapRenderer renderer = new MapRenderer(assetManager, map);
        attachTiles(renderer.getTiles(), application.getRootNode());
    }

    private void attachTiles(@NotNull List<HexTile> tiles, @NotNull Node rootNode) {
        tiles.forEach(hexTile -> hexTile.attachAsChild(rootNode));
    }

    public void update(float tpf, SimpleApplication application) {
        // make the player rotate:
        //map.rotate(0, tpf, 0);
    }
}
