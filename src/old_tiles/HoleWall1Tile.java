package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class HoleWall1Tile extends Tile {
    public static final int ID = 12;
    
    public HoleWall1Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_hole_1");
    }
    
    public String getName() {
        return "Hole Wall 1";
    }

    public int getID() {
        return ID;
    }
}