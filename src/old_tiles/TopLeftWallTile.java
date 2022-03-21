package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopLeftWallTile extends Tile {
    public static final int ID = 1;
    
    public TopLeftWallTile(PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_top_left");
    }

    public String getName() {
        return "Top Left Wall";
    }

    public int getID() {
        return ID;
    }
}
