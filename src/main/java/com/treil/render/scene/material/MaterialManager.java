package com.treil.render.scene.material;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

/**
 * @author Nicolas
 * @since 28/09/2017.
 */
public class MaterialManager {
    private AssetManager assetManager;
    private final Material lowPolyMaterial;

    public MaterialManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        lowPolyMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        lowPolyMaterial.setTexture("DiffuseMap", assetManager.loadTexture("lowpoly/lowpoly-texture.jpg"));
    }

    public Material getLowPolyMaterial() {
        return lowPolyMaterial;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
