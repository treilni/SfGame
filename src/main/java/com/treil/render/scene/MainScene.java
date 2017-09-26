package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.treil.render.geom.Angle;
import com.treil.render.scene.tile.HexTile;

/**
 * @author Nicolas
 * @since 13/09/2017.
 */
public class MainScene implements Scene {
    public void init(SimpleApplication application) {
        AssetManager assetManager = application.getAssetManager();

        Material mat = getUnshadedMaterial(assetManager, ColorRGBA.Green);
        Material borderMat = getUnshadedMaterial(assetManager, ColorRGBA.Red);
        float radius = 2f;
        float xStep = (float) (2 * radius * Math.cos(Angle.DEG_30));
        for (int x = -1; x < 2; x++) {
            HexTile tile = new HexTile(x * xStep, 0, 2, mat, borderMat);
            tile.attachAsChild(application.getRootNode());
        }
    }

    private Material getUnshadedMaterial(AssetManager assetManager, ColorRGBA color) {
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        return mat;
    }

    public void update(float tpf, SimpleApplication application) {
        // make the player rotate:
        //map.rotate(0, tpf, 0);
    }
}
