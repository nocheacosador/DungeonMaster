package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopSideLeftWallTile extends Tile {
    public static final int ID = 35;
    public TopSideLeftWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_side_top_left");
    }
    
    public String getName() {
        return "Top Side Left Wall";
    }

    public int getID() {
        return ID;
    }
}