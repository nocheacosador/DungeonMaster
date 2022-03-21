package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class FrontLeftSideWallTile extends Tile {
    public static final int ID = 39;
    public FrontLeftSideWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_side_front_left");
    }
    
    public String getName() {
        return "Front Left Side Wall";
    }

    public int getID() {
        return ID;
    }
}