package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class RightCornerWallTile extends Tile {
    public static final int ID = 44;
    public RightCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_right");
    }
    
    public String getName() {
        return "RightCornerWall";
    }

    public int getID() {
        return ID;
    }
}