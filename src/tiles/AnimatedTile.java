package tiles;

import rendering.Texture;

public class AnimatedTile extends Tile {
    public int animationSpeed = 600;

    protected Texture[] textures;

    public AnimatedTile(int id, String name, Texture[] textures) {
        super(id, name, textures[0]);

        this.textures = textures;
    }

    public void update(int millis) {
        setTexture(textures[millis / animationSpeed % textures.length]);
    }
}
