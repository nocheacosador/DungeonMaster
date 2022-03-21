package game.characters;

import rendering.Texture;
import rendering.TextureManager;
import tiles.TileMap;

class BigZombie extends Character {
    private enum State {
        Searching, Cooldown, Found
    };
    
    private State state = State.Searching;
    private int timepoint = 0;
    private int lastAttack = 0;
    private float sightLenght = TileMap.TILE_SIZE * 9;
    
    public BigZombie() {
        super();
    }

    @Override
    public void init() {
        idleTextures = new Texture[4];
        runTextures = new Texture[4];

        TextureManager tm = TextureManager.getInstance();

        idleTextures[0] = tm.getTexture("big_zombie_idle_anim_f0");
        idleTextures[1] = tm.getTexture("big_zombie_idle_anim_f1");
        idleTextures[2] = tm.getTexture("big_zombie_idle_anim_f2");
        idleTextures[3] = tm.getTexture("big_zombie_idle_anim_f3");

        runTextures[0] = tm.getTexture("big_zombie_run_anim_f0");
        runTextures[1] = tm.getTexture("big_zombie_run_anim_f1");
        runTextures[2] = tm.getTexture("big_zombie_run_anim_f2");
        runTextures[3] = tm.getTexture("big_zombie_run_anim_f3");

        hitTexture = tm.getTexture("big_zombie_idle_anim_f2");

        maxHealth = 16;
        attackStrength = 5;
        attackRadius = TileMap.TILE_SIZE * 1.5f;
        attackPeriod = 2000;
        speed = 70.f;
    }

    @Override
    public void update(int millis, TileMap map, Character player) {
        if (player != null) {
            float[] pp = player.getPosition();
            float dx = pp[0] - x;
            float dy = pp[1] - y;
            float distanceToPLayerSq = dx * dx + dy * dy;
            
            if (distanceToPLayerSq <= attackRadius * attackRadius / 4) {
                if (state != State.Found) {
                    state = State.Found;
                    timepoint = millis;
                }
                setMove(STILL, STILL);
            }
            else if (distanceToPLayerSq <= sightLenght * sightLenght) {
                if (millis - timepoint > 3000 && state == State.Searching) {
                    state = State.Cooldown;
                    timepoint = millis;
                }
                else if (millis - timepoint > 2000 && state != State.Searching) {
                    state = State.Searching;
                    timepoint = millis;
                }
                
                if (state == State.Searching) {
                    int horizontal = dx > 0 ? RIGHT : LEFT;
                    int vertical = dy < 0 ? UP : DOWN;

                    float ratio = Math.abs(dx) / (Math.abs(dy) + Math.abs(dx));

                    if (ratio > 0.75)        setMove(horizontal, STILL);
                    else if (ratio < 0.25)   setMove(STILL, vertical);
                    else {
                        setMove(horizontal, vertical);
                    }
                }
            }
            else {
                setMove(STILL, STILL);
            }

            if (millis - lastAttack >= attackPeriod && state != State.Cooldown && distanceToPLayerSq <= attackRadius * attackRadius) {
                player.getDamage(attackStrength);
                lastAttack = millis;
                timepoint = millis;
            }
        }
        super.update(millis, map, player);
    }
}
