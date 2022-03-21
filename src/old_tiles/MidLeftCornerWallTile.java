package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class MidLeftCornerWallTile extends Tile {
    public static final int ID = 51;
    public MidLeftCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_inner_corner_mid_left");
    }
    
    public String getName() {
        return "Mid Left Corner Wall";
    }

    public int getID() {
        return ID;
    }
}