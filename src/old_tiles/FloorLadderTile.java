package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class FloorLadderTile extends Tile {
    public static final int ID = 33;
    public FloorLadderTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("floor_ladder");
    }
    
    public String getName() {
        return "Floor Ladder";
    }

    public int getID() {
        return ID;
    }
}