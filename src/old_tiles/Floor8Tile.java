package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor8Tile extends Tile {
    public static final int ID = 32;
    public static final boolean stackable = true;
    
    public Floor8Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_8");
    }
    
    public String getName() {
        return "Floor 8";
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