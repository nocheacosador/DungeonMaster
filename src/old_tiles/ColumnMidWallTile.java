package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class ColumnMidWallTile extends Tile {
    public static final int ID = 22;
    public ColumnMidWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_column_mid");
    }
    
    public String getName() {
        return "Column Mid Wall";
    }

    public int getID() {
        return ID;
    }
}