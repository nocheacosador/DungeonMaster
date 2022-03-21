package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class GreenBannerWallTile extends Tile {
    public static final int ID = 16;
    public GreenBannerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_banner_green");
    }
    
    public String getName() {
        return "Green Banner Wall";
    }

    public int getID() {
        return ID;
    }
}