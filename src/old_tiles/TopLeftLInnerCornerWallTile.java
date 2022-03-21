package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopLeftLInnerCornerWallTile extends Tile {
    public static final int ID = 49;
    public TopLeftLInnerCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_inner_corner_l_top_left");
    }
    
    public String getName() {
        return "Top Left L Inner Corner Wall";
    }

    public int getID() {
        return ID;
    }
}