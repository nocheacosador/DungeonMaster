package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopMidWallTile extends Tile {
    public static final int ID = 2; 
    
    public TopMidWallTile(PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_top_mid");
    }

    public String getName() {
        return "Top Mid Wall";
    }

    public int getID() {
        return ID;
    }
}
