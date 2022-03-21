package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor5Tile extends Tile {
    public static final int ID = 30;
    public static final boolean stackable = true;
    
    public Floor5Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_5");
    }
    
    public String getName() {
        return "Floor 5";
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