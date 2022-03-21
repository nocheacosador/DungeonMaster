package old_tiles;

import processing.core.PApplet;
import rendering.TextureManager;

public class TopRightWallTile extends Tile {
    public static final int ID = 3;

    public TopRightWallTile(PApplet parent) {
        super(parent);
        this.texture = TextureManager.getInstance().getTexture("wall_top_right");
    }

    public String getName() {
        return "Top Rigth Wall";
    }

    public int getID() {
        return ID;
    }
}
