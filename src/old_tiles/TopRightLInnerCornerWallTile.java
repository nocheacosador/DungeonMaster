package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopRightLInnerCornerWallTile extends Tile {
    public static final int ID = 50;
    public TopRightLInnerCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_inner_corner_l_top_rigth");
    }
    
    public String getName() {
        return "Top Right L Inner Corner Wall";
    }

    public int getID() {
        return ID;
    }
}