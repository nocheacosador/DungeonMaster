package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class HoleTile extends Tile {
    public static final int ID = 56;
    public HoleTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("hole");
    }
    
    public String getName() {
        return "Hole";
    }

    public int getID() {
        return ID;
    }
}