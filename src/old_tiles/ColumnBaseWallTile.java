package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class ColumnBaseWallTile extends Tile {
    public static final int ID = 23;
    public ColumnBaseWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_coulmn_base");
    }
    
    public String getName() {
        return "Column Base Wall";
    }

    public int getID() {
        return ID;
    }
}