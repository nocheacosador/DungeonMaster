package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopLeftTInnerCornerWallTile extends Tile {
    public static final int ID = 53;
    public TopLeftTInnerCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_inner_corner_t_top_left");
    }
    
    public String getName() {
        return "Top Left T Inner Corner Wall";
    }

    public int getID() {
        return ID;
    }
}