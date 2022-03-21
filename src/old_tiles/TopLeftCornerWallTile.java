package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopLeftCornerWallTile extends Tile {
    public static final int ID = 41;
    public TopLeftCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_top_left");
    }
    
    public String getName() {
        return "Top Left Corner Wall";
    }

    public int getID() {
        return ID;
    }
}