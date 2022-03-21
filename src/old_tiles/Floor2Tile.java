package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor2Tile extends Tile {
    public static final int ID = 27;
    public static final boolean stackable = true;
    
    public Floor2Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_2");
    }
    
    public String getName() {
        return "Floor 1";
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