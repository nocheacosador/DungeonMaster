package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class RightWallTile extends Tile {
    public static final int ID = 6;
    
    public RightWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_right");
    }

    public String getName() {
        return "Right Wall";
    }

    public int getID() {
        return ID;
    }
}