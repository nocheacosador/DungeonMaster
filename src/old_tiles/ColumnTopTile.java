package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class ColumnTopTile extends Tile {
    public static final int ID = 18;
    public ColumnTopTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("column_top");
    }
    
    public String getName() {
        return "Column top";
    }

    public int getID() {
        return ID;
    }
}