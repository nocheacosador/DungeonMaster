package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class EdgeTile extends Tile {
    public static final int ID = 55;
    public EdgeTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("edge");
    }
    
    public String getName() {
        return "Edge";
    }

    public int getID() {
        return ID;
    }
}