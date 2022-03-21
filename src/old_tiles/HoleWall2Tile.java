package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class HoleWall2Tile extends Tile {
    public static final int ID = 13;
    
    public HoleWall2Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_hole_2");
    }
    
    public String getName() {
        return "Hole Wall 2";
    }

    public int getID() {
        return ID;
    }
}