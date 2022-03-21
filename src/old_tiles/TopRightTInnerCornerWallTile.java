package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopRightTInnerCornerWallTile extends Tile {
    public static final int ID = 54;
    public TopRightTInnerCornerWallTile(PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_inner_corner_t_top_rigth");
    }
    
    public String getName() {
        return "Top Right T Inner Corner Wall";
    }

    public int getID() {
        return ID;
    }
}