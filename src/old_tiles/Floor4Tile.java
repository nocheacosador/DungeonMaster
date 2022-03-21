package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor4Tile extends Tile {
    public static final int ID = 29;
    public static final boolean stackable = true;
    
    public Floor4Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_4");
    }
    
    public String getName() {
        return "Floor 4";
    }

    public int getID() {
        return ID;
    }
    
    public boolean isStackable() {
        return stackable;
    }

    public void stack(Tile tile) {
        next = tile;
    }
}