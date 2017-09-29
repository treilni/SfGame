package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
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
        final Node rootNode = application.getRootNode();
        attachTiles(renderer.getTiles(), rootNode);

        // Sunlight
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0, -1, 0).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        // A little bit of ambient light
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(new ColorRGBA(ColorRGBA.LightGray).interpolateLocal(ColorRGBA.DarkGray, 0.6f));
        rootNode.addLight(ambientLight);
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
