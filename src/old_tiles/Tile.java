package old_tiles;
import processing.core.PApplet;
import rendering.Texture;

public abstract class Tile {
    public static final int ID = -1;
    public static final boolean stackable = false;
    
    protected Texture texture;
    protected PApplet parent;
    
    protected Tile next;

    public Tile(PApplet parent) {
        this.parent = parent;
    }

    public void draw() {
        draw(32);
    }

    public void draw(float size) {
        parent.noStroke();
        parent.textureMode(PApplet.IMAGE); 
        parent.beginShape();
        parent.texture(texture.texture);
        parent.vertex(0,    0,    texture.left,  texture.top);
        parent.vertex(size, 0,    texture.right, texture.top);
        parent.vertex(size, size, texture.right, texture.bottom);
        parent.vertex(0,    size, texture.left,  texture.bottom);
        parent.endShape();

        if (next != null) {
            next.draw(size);
        }
    }

    public int getID() {
        return ID;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void stack(Tile tile) {
    }

    public Tile getNext() {
        return next;
    }

    public abstract String getName();
}
