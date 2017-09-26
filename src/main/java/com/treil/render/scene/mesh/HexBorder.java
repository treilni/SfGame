package com.treil.render.scene.mesh;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.treil.render.geom.Angle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas
 * @since 25/09/2017.
 */
public class HexBorder extends Mesh {
    private static final float heightOffset = 0.001f;

    public HexBorder(float x, float y, float radius, float borderSizePct) {
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> texCoord = new ArrayList<>();
        double angle = Angle.DEG_30;
        float start = radius * (100f - borderSizePct) / 100f;
        float height = heightOffset;
        for (int i = 0; i < 6; i++, angle += Angle.DEG_60) {
            vertices.add(new Vector3f(x + (float) (Math.cos(angle) * start), height, (float) (y + (Math.sin(angle) * start))));
            texCoord.add(new Vector2f(0, 0));
            vertices.add(new Vector3f(x + (float) (Math.cos(angle) * radius), height, (float) (y + (Math.sin(angle) * radius))));
            texCoord.add(new Vector2f(0, 0));
        }

        int[] indexes = new int[6 * 2 * 3]; // Six borders, 2 triangles / border, 3 points / triangle
        int j = 0;
        int pivot = 0;
        for (int b = 0; b < 6; b++, pivot += 2) {
            // triangle 1
            indexes[j++] = pivot;
            indexes[j++] = (pivot + 2) % 12;
            indexes[j++] = pivot + 1;
            // triangle 2
            indexes[j++] = pivot + 1;
            indexes[j++] = (pivot + 2) % 12;
            indexes[j++] = (pivot + 3) % 12;
        }

        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices.toArray(new Vector3f[0])));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord.toArray(new Vector2f[0])));
        setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        updateBound();
    }
}
