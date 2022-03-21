package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class FrontLeftCornerWallTile extends Tile {
    public static final int ID = 47;
    public FrontLeftCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_front_left");
    }
    
    public String getName() {
        return "Front Left Corner Wall";
    }

    public int getID() {
        return ID;
    }
}