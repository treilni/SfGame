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
    public Hex(float x, float y, float radius) {
        Vector3f[] vertices = new Vector3f[6];
        double angle = Angle.DEG_30;
        for (int i = 0; i < vertices.length; i++, angle += Angle.DEG_60) {
            vertices[i] = new Vector3f(x + (float) (Math.cos(angle) * radius), -2, (float) (y + (Math.sin(angle) * radius)));
        }

        Vector2f[] texCoord = new Vector2f[vertices.length];
        for (int i = 0; i < texCoord.length; i++) {
            texCoord[i] = new Vector2f(0, 0);
        }

        int[] indexes = new int[4 * 3]; // 4 triangles, 3 points / triangle
        int j = 0;
        for (int i = 0; i < 4; i++) {
            indexes[j++] = 0;
            indexes[j++] = i + 2;
            indexes[j++] = i + 1;
        }

        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        updateBound();
    }
}
