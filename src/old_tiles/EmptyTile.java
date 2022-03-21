package old_tiles;

import processing.core.PApplet;

public class EmptyTile extends Tile {
    public static final int ID = 0;

    public EmptyTile(PApplet parent) {
        super(parent);
    }

    public void draw(float size) {
        
    }

    public String getName() {
        return "Empty";
    }

    public int getID() {
        return ID;
    }
}