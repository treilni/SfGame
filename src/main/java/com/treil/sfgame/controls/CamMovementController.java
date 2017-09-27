package com.treil.sfgame.controls;

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
    private static final int extentOvershootPct = 5;
    private static final float movementSpeed = 4.0f;

    @Nonnull
    private Camera camera;
    @Nonnull
    private Vector3f extent = new Vector3f();

    public CamMovementController(@Nonnull Camera camera) {
        this.camera = camera;
    }

    public void center() {
        camera.setLocation(new Vector3f(extent.getX(), 2, 10));
    }

    void moveRight(float value, float tpf) {
        Vector3f location = camera.getLocation().add(movementSpeed * value, 0f, 0f);
        location = constrainLocation(location);
        camera.setLocation(location);
    }

    void moveLeft(float value, float tpf) {
        moveRight(-value, tpf);
    }

    void moveForward(float value, float tpf) {
        moveBackward(-value, tpf);
    }

    void moveBackward(float value, float tpf) {
        Vector3f location = camera.getLocation().add(0f, 0f, movementSpeed * value);
        location = constrainLocation(location);
        camera.setLocation(location);
    }

    @Nonnull
    private Vector3f constrainLocation(@Nonnull Vector3f location) {
        float minX = -extent.getX() * extentOvershootPct / 100;
        float maxX = extent.getX() - minX;
        float minZ = -extent.getZ() * extentOvershootPct / 100;
        float maxZ = extent.getZ() - minZ;

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
