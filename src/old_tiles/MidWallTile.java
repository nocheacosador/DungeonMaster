package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class MidWallTile extends Tile {
    public static final int ID = 5;

    public MidWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_mid");
    }

    public String getName() {
        return "Mid Wall";
    }

    public int getID() {
        return ID;
    }
}