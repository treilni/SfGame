package com.treil.sfgame.controls;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 27/09/2017.
 */
public class CamMovementController {
    public static final Logger logger = LoggerFactory.getLogger(CamMovementController.class);
    private static final int EXTENT_OVERSHOOT_PCT = 5;
    private static final float MOVEMENT_SPEED = 10.0f;
    private static final float ROTATION_SPEED = 6f;
    private static final float ZOOM_SPEED = 20.0f;

    @Nonnull
    private FlyByCamera flyByCamera;
    @Nonnull
    private Camera camera;
    @Nonnull
    private Vector3f extent = new Vector3f();

    public CamMovementController(@Nonnull FlyByCamera flyByCamera, Camera camera, AppStateManager stateManager, InputManager inputManager) {
        this.flyByCamera = flyByCamera;
        this.camera = camera;
        flyByCamera.setMoveSpeed(MOVEMENT_SPEED);
        flyByCamera.setRotationSpeed(ROTATION_SPEED);
        flyByCamera.setZoomSpeed(ZOOM_SPEED);
        flyByCamera.setDragToRotate(true);

        stateManager.attach(new AbstractAppState() {
            @Override
            public void initialize(AppStateManager stateManager, com.jme3.app.Application app) {
                super.initialize(stateManager, app);
                redefineKeys(inputManager);
                stateManager.detach(this);
            }
        });

//        flyByCamera.setMotionAllowedListener((position, velocity) -> {
//            constrainLocation(position);
//            velocity.setY(0f);
//        });
    }

    private void redefineKeys(InputManager inputManager) {
        inputManager.deleteMapping("FLYCAM_Forward");
        inputManager.deleteMapping("FLYCAM_Backward");
        inputManager.deleteMapping("FLYCAM_Rise");
        inputManager.deleteMapping("FLYCAM_Lower");
        inputManager.deleteMapping("FLYCAM_StrafeLeft");
        inputManager.deleteMapping("FLYCAM_StrafeRight");
        inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(flyByCamera,
                "FLYCAM_Forward", "FLYCAM_Backward", "FLYCAM_StrafeLeft", "FLYCAM_StrafeRight");
    }

    public void center() {
        final Vector3f location = new Vector3f(extent.getX() / 2, 2, extent.getZ() / 2 + 10f);
        final Quaternion rotation = camera.getRotation();
        rotation.set(0f, 1f, -0.2f, 0f);
        camera.setRotation(rotation);
        camera.setLocation(constrainLocation(location));
    }

    @Nonnull
    private Vector3f constrainLocation(@Nonnull Vector3f location) {
        float minX = -extent.getX() * EXTENT_OVERSHOOT_PCT / 100;
        float maxX = extent.getX() - minX;
        float minZ = -extent.getZ() * EXTENT_OVERSHOOT_PCT / 100;
        float maxZ = extent.getZ() - minZ;
        minZ += 5f;
        maxZ += 5f;

        float x = location.getX();
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
        float z = location.getZ();
        if (z < minZ) {
            z = minZ;
        }
        if (z > maxZ) {
            z = maxZ;
        }
        return new Vector3f(x, location.getY(), z);
    }

    public void setExtent(@Nonnull Vector3f extent) {
        this.extent = extent;
    }
}
