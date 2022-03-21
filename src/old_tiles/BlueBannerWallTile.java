package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class BlueBannerWallTile extends Tile {
    public static final int ID = 15;
    public BlueBannerWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_banner_blue");
    }
    
    public String getName() {
        return "Blue Banner Wall";
    }

    public int getID() {
        return ID;
    }
}