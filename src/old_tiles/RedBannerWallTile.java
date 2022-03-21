package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class RedBannerWallTile extends Tile {
    public static final int ID = 14;
    public RedBannerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_banner_red");
    }
    
    public String getName() {
        return "Red Banner Wall";
    }

    public int getID() {
        return ID;
    }
}