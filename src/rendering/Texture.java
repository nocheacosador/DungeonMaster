package rendering;
import processing.core.PImage;

public class Texture {
    public final PImage texture;
    public final int left;
    public final int top;
    public final int right;
    public final int bottom;

    public Texture(PImage texture, int left, int top, int right, int bottom) {
        this.texture = texture;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getWidth() {
        return right - left;
    }

    public int getHeight() {
        return bottom - top;
    }
}