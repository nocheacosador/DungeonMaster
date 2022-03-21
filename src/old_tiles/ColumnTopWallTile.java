package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class ColumnTopWallTile extends Tile {
    public static final int ID = 21;
    public ColumnTopWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_column_top");
    }
    
    public String getName() {
        return "Column Top Wall";
    }

    public int getID() {
        return ID;
    }
}