package game.characters;

import rendering.Texture;
import rendering.TextureManager;

class Demon extends Character {
    public Demon(String name) {
        super(name);
    }

    @Override
    public void init() {
        idleTextures = new Texture[6];
        runTextures = idleTextures;
        
        TextureManager tm = TextureManager.getInstance();

        idleTextures[0] = tm.getTexture("demon_idle_f0");
        idleTextures[1] = tm.getTexture("demon_idle_f1");
        idleTextures[2] = tm.getTexture("demon_idle_f2");
        idleTextures[3] = tm.getTexture("demon_idle_f3");
        idleTextures[4] = tm.getTexture("demon_idle_f4");
        idleTextures[5] = tm.getTexture("demon_idle_f5");

        hitTexture = idleTextures[0];

        maxHealth = 50;
        attackStrength = 5;
        //attackRadius = TileMap.TILE_SIZE * 1.5f;
        attackPeriod = 2000;
        speed = 70.f;
        scale = 3;
    }
}
