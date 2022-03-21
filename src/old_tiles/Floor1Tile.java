package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class Floor1Tile extends Tile {
    public static final int ID = 26;
    public static final boolean stackable = true;
    
    public Floor1Tile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_1");
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