package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class LeftWallTile extends Tile {
    public static final int ID = 4;

    public LeftWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_left");
    }

    public String getName() {
        return "Left Wall";
    }

    public int getID() {
        return ID;
    }
}