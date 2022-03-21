package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class FrontRightSideWallTile extends Tile {
    public static final int ID = 40;
    public FrontRightSideWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_side_front_right");
    }
    
    public String getName() {
        return "Front Right Side Wall";
    }

    public int getID() {
        return ID;
    }
}