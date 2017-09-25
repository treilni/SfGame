package com.treil.render.scene.mesh;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Nicolas
 * @since 26/01/2017.
 */
public class TerrainMesh extends Mesh {
    private static final Logger logger = LoggerFactory.getLogger(TerrainMesh.class);
    private final int xExtent;
    private final int zExtent;
    private final float step;
    private final int xSteps;
    private final int zSteps;
    private Vector3f[] vertices;

    public TerrainMesh(int xExtent, int zExtent, float step, boolean doInit) {
        this.xExtent = xExtent;
        this.zExtent = zExtent;
        this.step = step;
        xSteps = (int) Math.rint(xExtent / step);
        zSteps = (int) Math.rint(zExtent / step);
        if (doInit) {
            init();
        }
    }

    protected void init() {
        long timeMs = System.currentTimeMillis();
        int npoints = (xSteps + 1) * (zSteps + 1);
        FloatBuffer positions = BufferUtils.createFloatBuffer(3 * npoints);
        FloatBuffer textureCoords = BufferUtils.createFloatBuffer(3 * npoints);
        float lineStartX = -(float) xExtent / 2;
        float z = -(float) zExtent / 2;
        float textureZ = 0;

        this.vertices = new Vector3f[(xSteps + 1) * (zSteps + 1)];
        int i = 0;
        for (int zc = 0; zc <= zSteps; zc++, z += step, textureZ += 1.0f) {
            float x = lineStartX;
            float textureX = 0.0f;
            for (int xc = 0; xc <= xSteps; xc++, x += step, textureX += 1.0f) {
                float y = initElevation(x, z);
                vertices[i++] = new Vector3f(x, y, z);
                positions.put(z).put(y).put(x);
                textureCoords.put(textureZ).put(textureX);
            }
        }
        positions.flip();
        textureCoords.flip();
        setBuffer(VertexBuffer.Type.Position, 3, positions);
        setBuffer(VertexBuffer.Type.TexCoord, 2, textureCoords);

        IntBuffer index = makeIndexBuffer(xSteps, zSteps);
        setBuffer(VertexBuffer.Type.Index, 3, index);
        FloatBuffer normals = makeNormalsBuffer(xSteps + 1, zSteps + 1);
        this.setBuffer(VertexBuffer.Type.Normal, 3, normals);

        setMode(Mode.Triangles);
        updateBound();
        updateCounts();
        setStatic();
        logger.info("Terrain {}x{} generated in {} ms", xSteps, zSteps,
                System.currentTimeMillis() - timeMs);
    }

    public float initElevation(float x, float z) {
        return 0.0f;
    }

    /**
     * a class to allow to access index -1 and lineLength of a line of data
     */
    private class LineWithRepeatedBounds<T> {
        private T[] line;

        private LineWithRepeatedBounds(@NotNull T[] buffer, int lineStart, int lineSize) {
            line = (T[]) Array.newInstance(buffer[0].getClass(), lineSize + 2);
            line[0] = buffer[lineStart];
            System.arraycopy(buffer, lineStart, line, 1, lineSize);
            line[lineSize + 1] = buffer[lineStart + lineSize - 1];
        }

        private T get(int i) {
            return line[i + 1];
        }
    }

    private FloatBuffer makeNormalsBuffer(int xSteps, int zSteps) {
        int n = xSteps * zSteps;
        FloatBuffer result = BufferUtils.createFloatBuffer(3 * n);
        LineWithRepeatedBounds<Vector3f> downLine = new LineWithRepeatedBounds(vertices, 0, xSteps);
        for (int lineStart = 0; lineStart < n; lineStart += xSteps) {
            int upLineStart = lineStart + xSteps;
            LineWithRepeatedBounds<Vector3f> upLine = new LineWithRepeatedBounds(vertices,
                    upLineStart >= n ? (zSteps - 1) * xSteps : upLineStart,
                    xSteps);
            LineWithRepeatedBounds<Vector3f> line = new LineWithRepeatedBounds(vertices, lineStart, xSteps);
            for (int x = 0; x < xSteps; x++) {
                float leftY = line.get(x - 1).getY();
                float rightY = line.get(x + 1).getY();
                float downY = downLine.get(x).getY();
                float upY = upLine.get(x).getY();
                Vector3f normal = new Vector3f(
                        (leftY - rightY) / step,
                        2.0f,
                        (downY - upY) / step);
                normal.normalize();
                result.put(normal.getZ()).put(normal.getY()).put(normal.getX());
            }
            downLine = line;
        }
        result.flip();
        return result;
    }

    private IntBuffer makeIndexBuffer(int xSteps, int zSteps) {
        IntBuffer index = BufferUtils.createIntBuffer(6 * xSteps * zSteps);
        for (int z = 0; z < zSteps; z++) {
            int nextLineStart = (z + 1) * (xSteps + 1);
            int lineStart = z * (xSteps + 1);
            for (int x = 0; x < xSteps; x++) {
                index.put((short) (nextLineStart + x));
                index.put((short) (lineStart + x));
                index.put((short) (lineStart + x + 1));
                index.put((short) (lineStart + x + 1));
                index.put((short) (nextLineStart + x + 1));
                index.put((short) (nextLineStart + x));
            }
        }
        index.flip();
        return index;
    }

    public int getxSteps() {
        return xSteps;
    }

    public int getzSteps() {
        return zSteps;
    }
}
