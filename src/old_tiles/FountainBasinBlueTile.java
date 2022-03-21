package old_tiles;

import processing.core.PApplet;
import rendering.Texture;
import rendering.TextureManager;

public class FountainBasinBlueTile extends Tile {
    public static final int ID = 11;

    public int animationSpeed = 600;

    private int lastUpdate = 0;
    private int textureIndex = 0;
    private Texture[] textures;

    public FountainBasinBlueTile(PApplet parent) {
        super(parent);
        this.textures = new Texture[3];

        this.textures[0] = TextureManager.getInstance().getTexture("wall_fountain_basin_blue_anim_f0");
        this.textures[1] = TextureManager.getInstance().getTexture("wall_fountain_basin_blue_anim_f1");
        this.textures[2] = TextureManager.getInstance().getTexture("wall_fountain_basin_blue_anim_f2");

        this.texture = textures[0];
    }

    public void draw(float size) {
        if (parent.millis() - lastUpdate > animationSpeed) {
            textureIndex++;
            textureIndex %= textures.length;
            lastUpdate = parent.millis();
            this.texture = textures[textureIndex];
        }

        super.draw(size);
    }

    public String getName() {
        return "Fountain Basin Blue";
    }

    public int getID() {
        return ID;
    }
}