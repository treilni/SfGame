package com.treil.sfgame.controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.treil.render.geom.HasExtent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 * @author Nicolas
 * @since 27/09/2017.
 */
public class CamMovementController {
    public static final Logger logger = LoggerFactory.getLogger(CamMovementController.class);
    private Camera camera;
    private float movementSpeed = 4.0f;

    public CamMovementController(Camera camera) {
        this.camera = camera;
    }

    public void center(@Nonnull HasExtent hasExtent) {
        final Vector3f extent = hasExtent.getExtent();
        camera.setLocation(new Vector3f(extent.getX(), 2, 10));
    }

    public void moveRight(float value, float tpf) {
        Vector3f location = camera.getLocation();
        location = location.add(movementSpeed * value, 0f, 0f);
        camera.setLocation(location);
    }

    public void moveLeft(float value, float tpf) {
        moveRight(-value, tpf);
    }

    public void moveForward(float value, float tpf) {
        moveBackward(-value, tpf);
    }

    public void moveBackward(float value, float tpf) {
        Vector3f location = camera.getLocation();
        location = location.add(0f, 0f, movementSpeed * value);
        camera.setLocation(location);
    }
}
