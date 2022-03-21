package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class LeftCornerWallTile extends Tile {
    public static final int ID = 43;
    public LeftCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_left");
    }
    
    public String getName() {
        return "Left Corner Wall";
    }

    public int getID() {
        return ID;
    }
}