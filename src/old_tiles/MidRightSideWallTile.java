package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class MidRightSideWallTile extends Tile {
    public static final int ID = 38;
    public MidRightSideWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_side_mid_right");
    }
    
    public String getName() {
        return "Mid Right Side Wall";
    }

    public int getID() {
        return ID;
    }
}