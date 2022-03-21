package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class GooBaseWallTile extends Tile {
    public static final int ID = 25;
    public GooBaseWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_goo_base");
    }
    
    public String getName() {
        return "Goo Base Wall";
    }

    public int getID() {
        return ID;
    }
}