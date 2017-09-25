package com.treil.render.geom;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 * @author Nicolas
 * @since 25/09/2017.
 */
public class Hex extends Mesh {
    public enum VertexDirection {
        EAST(0.0), NORTH_EAST(60.0), NORTH_WEST(120.0), WEST(180.0), SOUTH_WEST(210.0), SOUTH_EAST(270.0);

        VertexDirection(double v) {

        }
    }

    public Hex(float x, float y, float radius) {
        Vector3f[] vertices = new Vector3f[6];
        double angle = Angle.;
        for (int i = 0; i < vertices.length; i++, angle += Angle.DEG_60) {
            vertices[i] = new Vector3f(x + (float) (Math.cos(angle) * radius), (float) (y + (Math.sin(angle) * radius)), 0);
        }

        Vector2f[] texCoord = new Vector2f[vertices.length];
        for (int i = 0; i < texCoord.length; i++) {
            texCoord[0] = new Vector2f(0, 0);
        }

        int[] indexes = {2, 0, 1, 1, 3, 2};

        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        updateBound();
    }
}
