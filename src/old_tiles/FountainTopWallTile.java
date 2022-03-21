package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class FountainTopWallTile extends Tile {
    public static final int ID = 7;

    public FountainTopWallTile (PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_fountain_top");
    }

    public String getName() {
        return "Fountain Top Wall";
    }

    public int getID() {
        return ID;
    }
}