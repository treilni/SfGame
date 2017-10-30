package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.treil.render.scene.tile.HexTile;
import com.treil.sfgame.game.GameEventListener;
import com.treil.sfgame.map.HexMap;
import com.treil.sfgame.player.Player;
import com.treil.sfgame.units.Unit;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Nicolas
 * @since 13/09/2017.
 */
public class MainScene implements Scene, GameEventListener {
    private MapRenderer mapRenderer;
    private UnitsRenderer unitsRenderer;

    public void init(@NotNull SimpleApplication application, @NotNull HexMap map, @NotNull List<Player> players) {
        AssetManager assetManager = application.getAssetManager();

        final Node rootNode = application.getRootNode();
        mapRenderer = new MapRenderer(assetManager, map, rootNode);
        unitsRenderer = new UnitsRenderer(assetManager, mapRenderer, rootNode);
        players.forEach(player -> unitsRenderer.registerUnits(player.getUnits()));

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

    public void update(float tpf, @Nonnull SimpleApplication application) {
        final Vector2f cursorPosition = application.getInputManager().getCursorPosition().clone();
        final Camera cam = application.getCamera();
        Vector3f click3d = cam.getWorldCoordinates(cursorPosition, 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(cursorPosition, 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);
        CollisionResults results = new CollisionResults();
        application.getRootNode().collideWith(ray, results);
        final HexTile tileUnderCursor = mapRenderer.getTileAt(results);
        mapRenderer.showPointedTile(tileUnderCursor);
        mapRenderer.renderMapUpdate();
    }

    public void onUnitUpdate(Unit unit) {
        unitsRenderer.onUnitUpdate(unit);
    }

    @Override
    @Nonnull
    public Vector3f getExtent() {
        return mapRenderer.getExtent();
    }
}
