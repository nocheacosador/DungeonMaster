package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor3Tile extends Tile {
    public static final int ID = 28;
    public static final boolean stackable = true;
    
    public Floor3Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_3");
    }
    
    public String getName() {
        return "Floor 3";
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