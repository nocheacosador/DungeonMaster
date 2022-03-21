package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class MidRightInnerCornerWallTile extends Tile {
    public static final int ID = 52;
    public MidRightInnerCornerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_inner_corner_mid_rigth");
    }
    
    public String getName() {
        return "Mid Right Inner Corner Wall";
    }

    public int getID() {
        return ID;
    }
}