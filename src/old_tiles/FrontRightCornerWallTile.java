package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class FrontRightCornerWallTile extends Tile {
    public static final int ID = 48;
    public FrontRightCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_front_right");
    }
    
    public String getName() {
        return "Front Right Corner Wall";
    }

    public int getID() {
        return ID;
    }
}