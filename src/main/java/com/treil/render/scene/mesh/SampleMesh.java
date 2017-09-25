package com.treil.render.scene.mesh;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 * @author Nicolas
 * @since 30/01/2017.
 */
public class SampleMesh extends Mesh {
    public SampleMesh() {
        Vector3f[] vertices = new Vector3f[3];
        vertices[0] = new Vector3f(0, 0, 0);
        vertices[1] = new Vector3f(3, 0, 0);
        vertices[2] = new Vector3f(0, 0, -3);

        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(1, 0);
        texCoord[2] = new Vector2f(0, 1);

        int[] indexes = {2, 0, 1};

        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        updateBound();
    }
}
