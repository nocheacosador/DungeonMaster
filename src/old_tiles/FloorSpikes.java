package old_tiles;

import processing.core.PApplet;
import rendering.Texture;
import rendering.TextureManager;

public class FloorSpikes extends Tile {
    public static final int ID = 34;

    public int animationSpeed = 600;

    private int lastUpdate = 0;
    private int textureIndex = 0;
    private Texture[] textures;

    public FloorSpikes(PApplet parent) {
        super(parent);
        this.textures = new Texture[4];

        this.textures[0] = TextureManager.getInstance().getTexture("floor_spikes_anim_f0");
        this.textures[1] = TextureManager.getInstance().getTexture("floor_spikes_anim_f1");
        this.textures[2] = TextureManager.getInstance().getTexture("floor_spikes_anim_f2"); 
        this.textures[3] = TextureManager.getInstance().getTexture("floor_spikes_anim_f3");

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
        return "Floor Spikes";
    }

    public int getID() {
        return ID;
    }
}
