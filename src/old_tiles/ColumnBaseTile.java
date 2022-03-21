package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class ColumnBaseTile extends Tile {
    public static final int ID = 20;
    public ColumnBaseTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("coulmn_base");
    }
    
    public String getName() {
        return "Column Base";
    }

    public int getID() {
        return ID;
    }
}