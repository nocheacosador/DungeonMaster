package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor6Tile extends Tile {
    public static final int ID = 31;
    public static final boolean stackable = true;
    
    public Floor6Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_7");
    }
    
    public String getName() {
        return "Floor 7";
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