package com.treil.sfgame;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import com.treil.render.geom.Size2D;
import com.treil.render.scene.MainScene;
import com.treil.sfgame.controls.Action;
import com.treil.sfgame.controls.CamMovementController;
import com.treil.sfgame.controls.ControlListener;
import com.treil.sfgame.controls.InputController;
import com.treil.sfgame.game.GameManager;
import com.treil.sfgame.gui.GuiManager;
import com.treil.sfgame.map.HexCell;
import com.treil.sfgame.map.HexDirection;
import com.treil.sfgame.map.HexMap;
import com.treil.sfgame.map.RandomMapGenerator;
import com.treil.sfgame.units.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 12/09/2017.
 */
@Component
public class Application extends SimpleApplication implements ControlListener {
    @Nonnull
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Nonnull
    private final MainScene scene = new MainScene();

    private HexMap map;
    private GameManager gameManager;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Application app = context.getBean(Application.class);
        app.start();
    }

    @Override
    public void start() {
        AppSettings settings = getAppSettings();
        setSettings(settings);
        setShowSettings(false);
        // setDisplayFps(false); // to hide the FPS
        setDisplayStatView(false);

        super.start();
        logger.info("Application started");
    }

    private AppSettings getAppSettings() {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setBitsPerPixel(32);
        settings.setAudioRenderer(null);
        return settings;
    }

    public void simpleInitApp() {
        map = new HexMap(20, 40, new RandomMapGenerator());

        gameManager = new GameManager(map);
        scene.init(this, map, gameManager.getPlayers());
        rootNode.updateModelBound();
        rootNode.updateGeometricState();

        final CamMovementController camMovementController = new CamMovementController(flyCam, cam, stateManager, inputManager);
        camMovementController.setExtent(scene.getExtent());
        camMovementController.center();
        InputController inputControler = new InputController(inputManager);
        logger.info("Initialized " + inputControler);
        inputControler.registerListener(this);

        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        GuiManager guiManager = new GuiManager(guiNode, getSize());

        logger.info("Initialized " + guiManager);
    }

    /* Use the main event loop to trigger repeating actions. */
    @Override
    public void simpleUpdate(float tpf) {
        scene.update(tpf, this);
    }

    @Nonnull
    public Size2D getSize() {
        final AppSettings appSettings = getAppSettings();
        return new Size2D(appSettings.getWidth(), appSettings.getHeight());
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void processAction(@Nonnull Action action) {
        final Unit unit = gameManager.getSelectedUnit();
        HexDirection direction = null;
        switch (action) {
            case LEFT:
                direction = HexDirection.WEST;
                break;
            case RIGHT:
                direction = HexDirection.EAST;
                break;
            case UP:
                direction = HexDirection.NORTH_EAST;
                break;
            case DOWN:
                direction = HexDirection.SOUTH_EAST;
                break;
            case FORWARD:
            case BACKWARD:
            case NONE:
                break;
        }
        if (direction != null && unit != null) {
            final HexCell newPosition = map.getSibling(unit.getPosition(), direction);
            if (newPosition != null) {
                logger.debug("New position : " + newPosition.getLocation());
                unit.setPosition(newPosition);
                scene.onUnitUpdate(unit);
            }
        }
    }
}
