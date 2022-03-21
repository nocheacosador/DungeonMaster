package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class BottomLeftCornerWallTile extends Tile {
    public static final int ID = 45;
    public BottomLeftCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_bottom_left");
    }
    
    public String getName() {
        return "Bottom Left Corner Wall";
    }

    public int getID() {
        return ID;
    }
}