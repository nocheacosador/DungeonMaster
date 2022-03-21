package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class GooWallTile extends Tile {
    public static final int ID = 24;
    public GooWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_goo");
    }
    
    public String getName() {
        return "Goo Wall";
    }

    public int getID() {
        return ID;
    }
}