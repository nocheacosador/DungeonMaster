package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class YellowBannerWallTile extends Tile {
    public static final int ID = 17;
    public YellowBannerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_banner_yellow");
    }
    
    public String getName() {
        return "Yellow Banner Wall";
    }

    public int getID() {
        return ID;
    }
}