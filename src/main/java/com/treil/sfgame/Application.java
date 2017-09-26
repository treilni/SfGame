package com.treil.sfgame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.treil.render.scene.MainScene;
import com.treil.render.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Nicolas
 * @since 12/09/2017.
 */
@Component
public class Application extends SimpleApplication {
    public static Logger logger = LoggerFactory.getLogger(Application.class);
    Scene scene = new MainScene();

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Application app = (Application) context.getBean(Application.class);
        app.start();
    }

    @Override
    public void start() {
        AppSettings settings = getAppSettings();
        setSettings(settings);
        setShowSettings(false);

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
        scene.init(this);
        cam.setLocation(new Vector3f(0, 2, 10));
    }

    /* Use the main event loop to trigger repeating actions. */
    @Override
    public void simpleUpdate(float tpf) {
        scene.update(tpf, this);
    }
}
