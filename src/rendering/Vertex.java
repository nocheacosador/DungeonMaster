package rendering;

public class Vertex {
    public final float x;
    public final float y;
    public final float textureX;
    public final float textureY;

    public Vertex(float x, float y) {
        this(x, y, 0, 0);
    }

    public Vertex(float x, float y, float textureX, float textureY) {
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
    }
}