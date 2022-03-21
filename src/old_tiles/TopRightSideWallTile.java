package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopRightSideWallTile extends Tile {
    public static final int ID = 36;
    public TopRightSideWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_side_top_right");
    }
    
    public String getName() {
        return "Top Right Side Wall";
    }

    public int getID() {
        return ID;
    }
}