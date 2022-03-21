package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class MidLeftSideWallTile extends Tile {
    public static final int ID = 37;
    public MidLeftSideWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_side_mid_left");
    }
    
    public String getName() {
        return "Mid Left Side Wall";
    }

    public int getID() {
        return ID;
    }
}