package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.treil.render.scene.tile.HexTile;
import com.treil.sfgame.map.HexMap;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Nicolas
 * @since 13/09/2017.
 */
public class MainScene implements Scene {
    private MapRenderer renderer;

    public void init(SimpleApplication application, HexMap map) {
        AssetManager assetManager = application.getAssetManager();

        renderer = new MapRenderer(assetManager, map);
        attachTiles(renderer.getTiles(), application.getRootNode());
    }

    private void attachTiles(@Nonnull List<HexTile> tiles, @Nonnull Node rootNode) {
        tiles.forEach(rootNode::attachChild);
    }

    public void update(float tpf, @Nonnull SimpleApplication application) {
        // make the player rotate:
        //map.rotate(0, tpf, 0);
    }

    @Override
    @Nonnull
    public Vector3f getExtent() {
        return renderer.getExtent();
    }
}
