package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopRightCornerWallTile extends Tile {
    public static final int ID = 42;
    public TopRightCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_corner_top_right");
    }
    
    public String getName() {
        return "Top Right Corner Wall";
    }

    public int getID() {
        return ID;
    }
}