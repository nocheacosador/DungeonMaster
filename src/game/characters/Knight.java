package game.characters;

import rendering.Texture;
import rendering.TextureManager;

class Knight extends Character {
    public Knight(String name) {
        super(name);
    }

    @Override
    public void init() {
        idleTextures = new Texture[4];
        runTextures = new Texture[4];
        
        TextureManager tm = TextureManager.getInstance();

        idleTextures[0] = tm.getTexture("knight_m_idle_anim_f0");
        idleTextures[1] = tm.getTexture("knight_m_idle_anim_f1");
        idleTextures[2] = tm.getTexture("knight_m_idle_anim_f2");
        idleTextures[3] = tm.getTexture("knight_m_idle_anim_f3");

        runTextures[0] = tm.getTexture("knight_m_run_anim_f0");
        runTextures[1] = tm.getTexture("knight_m_run_anim_f1");
        runTextures[2] = tm.getTexture("knight_m_run_anim_f2");
        runTextures[3] = tm.getTexture("knight_m_run_anim_f3");

        hitTexture = tm.getTexture("knight_m_hit_anim_f0");

        maxHealth = 20;
    }
}