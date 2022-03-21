package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class ColumnMidTile extends Tile {
    public static final int ID = 19;
    public ColumnMidTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("column_mid");
    }
    
    public String getName() {
        return "Column Mid";
    }

    public int getID() {
        return ID;
    }
}