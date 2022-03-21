package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class BottomRightCornerWallTile extends Tile {
    public static final int ID = 46;
    public BottomRightCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_bottom_right");
    }
    
    public String getName() {
        return "Bottom Right Corner Wall";
    }

    public int getID() {
        return ID;
    }
}